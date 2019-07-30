package kiri.nstp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.ResourceGroupDao;
import kiri.nstp.dao.UserGroupRoleDao;
import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;
import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.exception.NoPermissionException;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.web.service.UserGroupRoleService;

@Service
public class UserGroupRoleServiceImpl implements UserGroupRoleService{
	@Autowired
	private UserGroupRoleDao ugrDao;
	@Autowired
	private ResourceGroupDao rgDao;

	@Value("${row5}")
	private Integer row;

	@Override
	@ResourcePermission(permission="readGroupRole")
	public List<UserGroupRoleBlock> getList(RoleSearchMessage rsm) {
		PageHelper.startPage(rsm.getPageNumber(), row);
		return ugrDao.list(rsm);
	}

		
	@Override
	@ResourcePermission(permission="grantGroupRole")
	public void addUserGroupRole(RoleSearchMessage rsm, UserGroupRoleBlock block) {
		//here means not a  group user as a result of wrong database operation
		if(rsm.getUsername()!= null && rsm.getUsername().length()!=0)
			throw new NoPermissionException();
		block.setGid(rgDao.list(block.getGname()).get(0).getGid());
		try {
			ugrDao.add(block);
		}catch(Exception e) {
			throw new DuplicatedInsertException();
		}
	}

	@Override
	@ResourcePermission(permission="deleteGroupRole")
	public void delUserGroupRole(RoleSearchMessage rsm) {
		List<UserGroupRoleBlock> list = ugrDao.list(rsm);
		if(list == null || list.isEmpty())
			throw new NoResourcePermissionException();
		ugrDao.del(list.get(0).getId());
	}

}
