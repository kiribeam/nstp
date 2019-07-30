package kiri.nstp.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.DiagnosticLogDao;
import kiri.nstp.dao.EcuInfoDao;
import kiri.nstp.dao.EcuKeyDao;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.DiagnosticLog;
import kiri.nstp.pojo.EcuInfo;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.security.CryptoUtils;
import kiri.nstp.util.DiagLogFactory;
import kiri.nstp.web.service.DiagnosticService;

@Service
public class DiagnosticServiceImpl implements DiagnosticService {

	@Autowired
	private DiagnosticLogDao diagDao;
	@Autowired
	private EcuInfoDao ecuDao;
	@Autowired
	private EcuKeyDao keyDao;
	@Autowired
	private DiagLogFactory logFac;
	
	@Value("${row5}")
	private Integer basicRow;
	@Value("${row10}")
	private Integer logRow;
	@Value("${diagResponsePadding}")
	private String padding;

	@Override
	@ResourcePermission(permission="readDiagLog")
	public List<DiagnosticLog> getLog(EcuSearchMessage esm) {
		PageHelper.startPage(esm.getPageNumber(), logRow);
		return diagDao.getLog(esm);
	}

	@Override
	@ResourcePermission(permission="doDiagExec")
	@Transactional(rollbackFor= {Exception.class})
	public Map<String, String> diagnostic(EcuSearchMessage esm, String head, String rand) {
		EcuInfo em = ecuDao.getEcuById(esm.getId());
		if(em == null) throw new WrongInputException();
		esm.setId(null);
		esm.setEcuid(em.getEcuid());
		List<EcuKey> el =  keyDao.getEcuKeys(esm);
		if(el == null || el.size() == 0)
			throw new NoResourcePermissionException();
		EcuKey ek = el.get(0);
		String hexKey = ek.getKeyValue();
		String challenge = head + ek.getEcuid() + rand;
		
		String sessionKey = CryptoUtils.doCmac(challenge, hexKey);
		String response = CryptoUtils.doCmac(padding, sessionKey);
		
		DiagnosticLog log = logFac.gen(ek.getEcuid(), SecurityUtils.getSubject().getPrincipal().toString());
		diagDao.addLog(log);

		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionKey", sessionKey);
		map.put("response", response);
		return map;
	}

}
