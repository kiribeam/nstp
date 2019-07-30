package kiri.nstp.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.dao.EcuGroupDao;
import kiri.nstp.dao.ResourceGroupDao;
import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.pojo.ResourceGroup;
import kiri.nstp.web.service.ResourceGroupService;

@Service
public class ResourceGroupServiceImpl implements ResourceGroupService {
	
	@Autowired
	private ResourceGroupDao rgDao;
	@Autowired
	private EcuGroupDao egDao;
	
	@Value("${row10}")
	private Integer groupRow;
	@Value("${statusValid}")
	private Integer statusValid;

	@Override
	public Set<ResourceGroup> getPermittedGroup(String username, String permission) {
		return new HashSet<ResourceGroup>(rgDao.getPermittedGroup(username, permission));
	}

	@Override
	public List<ResourceGroup> list(String group, Integer page) {
		PageHelper.startPage(page, groupRow);
		return rgDao.list(group);
	}

	@Override
	public void add(ResourceGroup rg) {
		rg.setStatus(statusValid);
		try{
			rgDao.add(rg);
		}catch(Exception e) {
			throw new DuplicatedInsertException();
		}
	}

	@Override
	@Transactional(rollbackFor= {Exception.class})
	public void del(Integer id) {
		ResourceGroup rg = rgDao.getById(id);
		egDao.delByGid(rg.getGid());
		rgDao.del(id);
	}



}
