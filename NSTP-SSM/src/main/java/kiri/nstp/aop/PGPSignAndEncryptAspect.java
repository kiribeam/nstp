package kiri.nstp.aop;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kiri.nstp.dao.PgpDao;
import kiri.nstp.dto.PgpSearchMessage;
import kiri.nstp.exception.UnregistedPgpException;
import kiri.nstp.exception.WrongPgpFileException;
import kiri.nstp.pojo.UserPgp;
import kiri.nstp.security.CryptoUtils;
import kiri.nstp.util.PgpSearchMessageFactory;

@Aspect
@Component("PGPStatusCheckAspect")
public class PGPSignAndEncryptAspect {

	@Autowired
	private PgpDao pgpDao;
	@Autowired
	private PgpSearchMessageFactory psmFac;

	@Value("${statusPkConfirmed}")
	private Integer PGP_PK_CONFIRMED;
	@Value("${pgpPkUploadDir}")
	private String pgpPkUploadDir;
	@Value("${pgpPkUploadFileSuffix}")
	private String pgpPkUploadFileSuffix;
	@Value("${serverPgpPrivateKey}")
	private String serverPrivateKey;
	@Value("${serverPgpPrivateKeyPass}")
	private String serverPgpPrivateKeyPass;	
	
	
	
	@Pointcut("@annotation(kiri.nstp.aop.PGPSignAndEncrypt)")
	public void pointcut() {}
	

	@Around("pointcut()")
	public Object testBefore(ProceedingJoinPoint pjp){
		checkPgpStatus();
		byte[] result;
		try{
			result = (byte[]) pjp.proceed();
		}catch(Throwable t) {
			throw new RuntimeException(t);
		}
		return doPgpSignAndEncrypt(result);
	}

	private void checkPgpStatus() {
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		PgpSearchMessage psm = psmFac.genBlank();
		psm.setUsername(username);
		List<UserPgp> pgpList = pgpDao.getPgpList(psm);
		if(pgpList == null || pgpList.isEmpty())
			throw new UnregistedPgpException();
		if(pgpList.get(0).getStatus() != PGP_PK_CONFIRMED)
			throw new UnregistedPgpException();	
	}
	
	private byte[] doPgpSignAndEncrypt(byte[] file) {
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		String pubkeyPath = pgpPkUploadDir + username + pgpPkUploadFileSuffix;
		byte[] pubs;
		try {
			FileInputStream fis = new FileInputStream(pubkeyPath);
			pubs = fis.readAllBytes();
			fis.close();
		}catch(IOException e) {
			throw new WrongPgpFileException();
		}
		PGPPublicKey pubkey = CryptoUtils.doGetPgpPublicKey(pubs);
		
		byte[] pris;
		try {
			FileInputStream fis = new FileInputStream(serverPrivateKey);
			pris = fis.readAllBytes();
			fis.close();
		}catch(IOException e) {
			throw new WrongPgpFileException();
		}
		PGPPrivateKey prikey = CryptoUtils.doGetPgpPrivateKey(pris, 
				serverPgpPrivateKeyPass.toCharArray());
		
		byte[] result = CryptoUtils.doPgpSignAndEncrypt(pubkey, prikey,
				file, "ok");
		return result;
	}
	
}
