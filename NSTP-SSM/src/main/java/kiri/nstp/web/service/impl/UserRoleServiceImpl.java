package kiri.nstp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.UserRoleDao;
import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;
import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.web.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleDao urDao;
	
	@Value("${row5}")
	private Integer row;


	@Override
	@ResourcePermission(permission="readPrivateRole")
	public List<UserGroupRoleBlock> getList(RoleSearchMessage rsm) {
		PageHelper.startPage(rsm.getPageNumber(), row);
		return urDao.list(rsm);
	}

	//Open for all manager's who has the right
	@Override
	public void addUserRole(UserGroupRoleBlock block) {
		try {
			urDao.add(block);
		}catch(Exception e) {
			throw new DuplicatedInsertException();
		}
	}

	//Require root!
	@Override
	public void delUserRole(Integer id) {
		urDao.del(id);
	}

}
