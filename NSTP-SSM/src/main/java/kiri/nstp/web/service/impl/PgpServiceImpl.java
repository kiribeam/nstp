package kiri.nstp.web.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.PgpDao;
import kiri.nstp.dto.PgpSearchMessage;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.pojo.UserPgp;
import kiri.nstp.security.CryptoUtils;
import kiri.nstp.util.UserPgpFactory;
import kiri.nstp.web.service.PgpService;
import kiri.utils.HexUtils;

@Service
public class PgpServiceImpl implements PgpService {

	@Autowired
	private PgpDao pgpDao;
	@Autowired
	private UserPgpFactory upFac; 
	/*
	@Autowired
	private UserLogDao logDao;
	@Autowired
	private UserLogFactory logFac;
	*/

	@Value("${row5}")
	private Integer row;
	@Value("${statusPkConfirmed}")
	private Integer statusPkConfirmed;
	@Value("${statusPkApplying}")
	private Integer statusPkApplying;
	@Value("${statusPkRevoked}")
	private Integer statusPkRevoked;
	@Value("${statusPkUnused}")
	private Integer statusPkUnused;
	@Value("${pgpPkUploadDir}")
	private String uploadPath;
	@Value("${pgpPkUploadFileSuffix}")
	private String pgpPkUploadFileSuffix;

	@Override
	@ResourcePermission(permission="readPgpPublicKey")
	public List<UserPgp> getPgpRecord(PgpSearchMessage psm) {
		PageHelper.startPage(psm.getPageNumber(), row);
		List<UserPgp> list = pgpDao.getPgpList(psm);
		return list;
	}

	@Transactional(rollbackFor= {Exception.class})
	@Override
	@ResourcePermission(permission="uploadPgpPublicKey")
	public void pgpUploadPublicKey(PgpSearchMessage psm, byte[] file) {
		List<UserPgp> list = pgpDao.getPgpList(psm);
		UserPgp up = null;
		String target = uploadPath + psm.getUsername() + pgpPkUploadFileSuffix;
		if(list != null && !list.isEmpty()) {
			up = list.get(0);
			if(up.getStatus() == statusPkApplying) {
				byte[] fp = CryptoUtils.doGetFingerPrint(file);
				String fingerprint = HexUtils.byteArrayToHexString(fp);
				up.setFingerPrint(fingerprint);
				pgpDao.updatePgp(up);
				writeFile(file, target);
			}
			return;
		}
		byte[] fp = CryptoUtils.doGetFingerPrint(file);
		String fingerPrint = HexUtils.byteArrayToHexString(fp);
		up = upFac.gen(psm.getUsername(), fingerPrint, statusPkApplying);
		pgpDao.createPgp(up);
		writeFile(file, target);
		//add user log
	}

	private void writeFile(byte[] file, String target) {
		try {
			FileOutputStream fos = new FileOutputStream(target);
			fos.write(file);
			fos.flush();
			fos.close();
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}	
	}

	@Transactional(rollbackFor= {Exception.class})
	@Override
	@ResourcePermission(permission="revokePgpPublicKey")
	public void pgpDropPublicKey(PgpSearchMessage psm) {
		List<UserPgp> list = pgpDao.getPgpList(psm);
		if(list==null || list.isEmpty()) throw new NoResourcePermissionException();
		UserPgp pgp = list.get(0);
		if(pgp.getStatus() == statusPkApplying) {
			pgpDao.delPgp(psm.getId());
			//UserLog log = logFac.gen(pgp.getUsername(), 22, pgp.getFingerPrint(), "");
			//logDao.addLog(log);
		}else if(pgp.getStatus() == statusPkConfirmed){
			pgp.setStatus(statusPkRevoked);
			pgpDao.updatePgp(pgp);
		}
	}

	@Transactional(rollbackFor= {Exception.class})
	@Override
	@ResourcePermission(permission="trustPgpPublicKey")
	public void pgpTrust(PgpSearchMessage psm, List<Integer> list) {
		for(Integer id : list) {
			psm.setId(id);
			List<UserPgp> ul = pgpDao.getPgpList(psm);
			if(ul == null || ul.isEmpty())
				throw new NoResourcePermissionException();
			UserPgp up = ul.get(0);
			if(up.getStatus() == statusPkUnused || up.getStatus() == statusPkConfirmed)
				continue;
			up.setStatus(statusPkConfirmed);
			pgpDao.updatePgp(up);
		}
	}

	@Transactional(rollbackFor= {Exception.class})
	@ResourcePermission(permission="untrustPgpPublicKey")
	@Override
	public void pgpUntrust(PgpSearchMessage psm, List<Integer> list) {
		for(Integer id : list) {
			psm.setId(id);
			List<UserPgp> ul = pgpDao.getPgpList(psm);
			if(ul == null || ul.isEmpty())
				throw new NoResourcePermissionException();
			UserPgp up = ul.get(0);
			if(up.getStatus() == statusPkUnused || up.getStatus() == statusPkRevoked)
				continue;
			up.setStatus(statusPkRevoked);
			pgpDao.updatePgp(up);
		}
	}

	@Transactional(rollbackFor= {Exception.class})
	@Override
	@ResourcePermission(permission="deletePgpPublicKey")
	public void pgpDelete(PgpSearchMessage psm, List<Integer> list) {
		for(Integer id : list) {
			psm.setId(id);
			List<UserPgp> ul = pgpDao.getPgpList(psm);
			if(ul == null || ul.isEmpty())
				throw new NoResourcePermissionException();
			UserPgp up = ul.get(0);
			pgpDao.delPgp(up.getId());
		}
	}
}
