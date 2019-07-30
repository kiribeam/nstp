package kiri.nstp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.EcuKeyChangeDao;
import kiri.nstp.dao.EcuKeyDao;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.pojo.EcuKeyChange;
import kiri.nstp.web.service.EcuKeyService;

@Service
public class EcuKeyServiceImpl implements EcuKeyService{
	
	@Autowired
	private EcuKeyDao keyDao;
	@Autowired
	private EcuKeyChangeDao keyChangeDao;

	@Value("${row5}")
	private Integer row;

	@Override
	@ResourcePermission(permission="readEcuKey")
	public List<EcuKey> getEcuKeys(EcuSearchMessage esm){
		PageHelper.startPage(esm.getPageNumber(), row);
		List<EcuKey> list = keyDao.getEcuKeys(esm);
		return list;
	}
	
	
	//init default ecu key, just a interface for uaes factory
	//I'll not do this!
	@Override
	public void initEcuKey(EcuKey ek) {
		//I guess
		//create ecu
		//set default key
		//set default refresh module
	}
	
	//only used for all delete 
	//not used at this project
	@Override
	public void delEcuKey(EcuKey ek) {
		keyDao.delEcuKey(ek.getEcuid());
	}
	
	@Override
	@ResourcePermission(permission="readEcuKeyChange")
	public List<EcuKeyChange> getKeyChange(EcuSearchMessage esm){
		PageHelper.startPage(esm.getPageNumber(), row);
		List<EcuKeyChange> list = keyChangeDao.getList(esm);
		return list;
	}
}
