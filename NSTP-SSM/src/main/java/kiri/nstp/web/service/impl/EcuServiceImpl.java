package kiri.nstp.web.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.EcuGroupDao;
import kiri.nstp.dao.EcuInfoDao;
import kiri.nstp.dao.EcuKeyChangeDao;
import kiri.nstp.dao.EcuKeyDao;
import kiri.nstp.dto.EcuGroupBlock;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.exception.OperationFailedException;
import kiri.nstp.pojo.EcuInfo;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.util.EcuGroupBlockFactory;
import kiri.nstp.util.EcuInfoFactory;
import kiri.nstp.util.EcuKeyFactory;
import kiri.nstp.web.service.EcuService;

@Service
public class EcuServiceImpl implements EcuService{
	
	@Autowired
	private EcuInfoDao ecuMessageDao;
	@Autowired
	private EcuKeyDao ecuKeyDao;
	@Autowired
	private EcuGroupDao egDao;
	@Autowired
	private EcuKeyChangeDao ekcDao;
	@Autowired
	private EcuGroupBlockFactory egbFac;
	@Autowired
	private EcuKeyFactory keyFac;
	@Autowired
	private EcuInfoFactory emFac;

	@Value("${row5}")
	private Integer row;
	@Value("${defaultKey}")
	private String defaultKey;
	@Value("${defaultKeyFlag}")
	private String defaultKeyFlag;
	@Value("${statusEcuKeyVerified}")
	private Integer keyVerified;
	@Value("${statusEcuDummy}")
	private Integer statusEcuDummy;
	@Value("${defaultDummyGid}")
	private Integer dummyGid;
	@Value("${createDuplicatedMaxCount}")
	private Integer maxCount;

	@Override
	@ResourcePermission(permission="readEcuInfo")
	public List<EcuInfo> getEcuList(EcuSearchMessage esm){
		PageHelper.startPage(esm.getPageNumber(), row);
		List<EcuInfo> list = ecuMessageDao.getEcuMessage(esm);
		return list;
	}

	

	@Override
	@ResourcePermission(permission="createDummyEcu")
	public void createEcu(Integer number) {
		int count = 0;
		for(int i=0; i<number; i++) {
			String ecuid = randomEcuid();
			try {
				createCustomEcu(ecuid);
			}catch(DuplicatedInsertException e) {
				count++;
				if(count < maxCount) i--;
				else throw new OperationFailedException();
			}
		}
	}

	private String randomEcuid() {
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString().replace("-", "").toUpperCase();
		return s;
	}

	@Override
	@Transactional
	@ResourcePermission(permission="createDummyEcu")
	public void createCustomEcu(String ecuid) {
		String username = SecurityUtils.getSubject().getPrincipal().toString(); 
		EcuInfo ecu = emFac.gen(ecuid, username,null, statusEcuDummy);
		try {
			ecuMessageDao.createEcu(ecu);
		}catch(Exception e) {
			throw new DuplicatedInsertException();
		}
		EcuKey ecuKey = keyFac.gen(ecuid, 1, 0, defaultKeyFlag, defaultKey, keyVerified);
		for(int i=1; i<16; i++) {
			ecuKey.setKeyId(i);
			ecuKeyDao.initDefaultEcuKey(ecuKey);
		}
		//default dummy group!
		EcuGroupBlock block = egbFac.genBlank();
		block.setEcuid(ecuid);
		block.setGid(dummyGid);
		egDao.add(block);
	}
	
	@Override
	@Transactional
	@ResourcePermission(permission="deleteEcu")
	public void delEcu(List<Integer> list, EcuSearchMessage esm) {
		for(Integer id : list) {
			esm.setId(id);
			delOneEcu(esm);
		}
	}

	private void delOneEcu(EcuSearchMessage esm) {
		List<EcuInfo> list = ecuMessageDao.getEcuMessage(esm);
		if(list == null || list.isEmpty())
			throw new NoResourcePermissionException();
		EcuInfo em = list.get(0);
		egDao.delByEcuid(em.getEcuid());
		ekcDao.delFollowEcu(em.getEcuid());
		ecuKeyDao.delEcuKey(em.getEcuid());
		ecuMessageDao.del(em.getId());
	}
}
