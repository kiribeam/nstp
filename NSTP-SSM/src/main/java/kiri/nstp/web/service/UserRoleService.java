package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;

public interface UserRoleService {
	List<UserGroupRoleBlock> getList(RoleSearchMessage rsm);

	void addUserRole(UserGroupRoleBlock block);
	void delUserRole(Integer id);
}
