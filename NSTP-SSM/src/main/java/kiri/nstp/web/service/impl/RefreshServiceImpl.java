package kiri.nstp.web.service.impl;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.PGPSignAndEncrypt;
import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.EcuInfoDao;
import kiri.nstp.dao.RefreshLogDao;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.pojo.EcuInfo;
import kiri.nstp.pojo.RefreshLog;
import kiri.nstp.security.CryptoUtils;
import kiri.nstp.security.RefreshUtils;
import kiri.nstp.util.RefreshLogFactory;
import kiri.nstp.web.service.RefreshService;
import kiri.utils.HexUtils;

@Service
public class RefreshServiceImpl implements RefreshService {
	
	@Autowired
	private RefreshLogDao refreshDao;
	@Autowired
	private EcuInfoDao ecuDao;
	@Autowired
	private RefreshLogFactory refFac;
	
	@Value("${row5}")
	private Integer row;
	@Value("${row10}")
	private Integer logRow;
	@Value("${refreshFileTypeSigned}")
	private String fileTypeSigned;
	@Value("${operationRefresh}")
	private Integer type;
	@Value("${refreshFileTypeSigned}")
	private String REFRESH_SIGNED_FILE_HEADER;
	@Value("${serverPrivateKeyPath}")
	private String serverPrivateKeyPath;

	@Override
	@Transactional(rollbackFor= {Exception.class})
	@PGPSignAndEncrypt
	@ResourcePermission(permission="signRefreshFile")
	public byte[] generateSignedFile(byte[] file, String header, String app,
			String signInfoHeader, EcuSearchMessage esm) {

		List<EcuInfo> list = ecuDao.getEcuMessage(esm);
		if(list == null || list.isEmpty())
			throw new NoResourcePermissionException();
		String ecuid = list.get(0).getEcuid();
		byte[] fileHeader = HexUtils.hexStringToByteArray(REFRESH_SIGNED_FILE_HEADER);
		byte[] head = HexUtils.hexStringToByteArray(
				header + ecuid + app);
		byte[] totalMd = CryptoUtils.doSHA256(file);
		KeyPair keyPair = CryptoUtils.doGenerateRSAKeyPair();
		RSAPublicKey dynamicPubKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey dynamicPriKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPrivateKey serverPriKey = RefreshUtils.getPrivateKey(serverPrivateKeyPath);
		byte[] signInfo = RefreshUtils.generateSignerInfo(serverPriKey, signInfoHeader, dynamicPubKey);
		LinkedList<byte[]> fileList = new LinkedList<>();
		fileList.add(head);
		fileList.add(totalMd);
		fileList.add(signInfo);
		byte[] totalSign = CryptoUtils.doSignBySHA256withRSA(fileList, dynamicPriKey);
		fileList.add(totalSign);
		fileList.add(file);
		fileList.addFirst(fileHeader);
		byte[] total = HexUtils.mergeByteArrays(fileList);

		String username = SecurityUtils.getSubject().getPrincipal().toString();
		RefreshLog log = refFac.gen(ecuid, type, username); 
		refreshDao.addLog(log);
		
		return total;
	}

	@Override
	@ResourcePermission(permission="readRefreshLog")
	public List<RefreshLog> getLog(EcuSearchMessage esm) {
		PageHelper.startPage(esm.getPageNumber(), logRow);
		return refreshDao.getLog(esm);
	}

}
