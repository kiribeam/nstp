package kiri.nstp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.EcuGroupDao;
import kiri.nstp.dao.ResourceGroupDao;
import kiri.nstp.dto.EcuGroupBlock;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.pojo.ResourceGroup;
import kiri.nstp.web.service.EcuGroupService;

@Service
public class EcuGroupServiceImpl implements EcuGroupService{
	
	@Autowired
	private EcuGroupDao egDao;
	@Autowired
	private ResourceGroupDao rgDao;
	@Value("${row5}")
	private Integer row;

	@ResourcePermission(permission="readEcuGroup")
	@Override
	public List<EcuGroupBlock> list(EcuSearchMessage esm) {
		PageHelper.startPage(esm.getPageNumber(), row);
		return egDao.list(esm);
	}

	@ResourcePermission(permission="addEcuIntoGroup")
	@Override
	public void add(EcuSearchMessage esm) {
		List<ResourceGroup> list = rgDao.list(esm.getUserGroup());
		if(list == null || list.isEmpty())
			throw new NoResourcePermissionException();
		ResourceGroup rg = list.get(0);

		List<EcuGroupBlock> blist = egDao.list(esm);
		if(list==null||list.isEmpty())
			throw new NoResourcePermissionException();
		EcuGroupBlock block = blist.get(0);
		block.setGid(rg.getGid());
		try {
			egDao.add(block);
		}catch(Exception e) {
			throw new DuplicatedInsertException();
		}
	}

	@Override
	@ResourcePermission(permission="removeEcuFromGroup")
	public void del(EcuSearchMessage esm) {
		List<EcuGroupBlock> list = egDao.list(esm);
		if(list == null || list.isEmpty())
			throw new NoResourcePermissionException();
		egDao.del(esm.getId());
	}

}
