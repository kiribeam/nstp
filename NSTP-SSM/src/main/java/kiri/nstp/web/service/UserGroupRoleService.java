package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;

public interface UserGroupRoleService {
	List<UserGroupRoleBlock> getList(RoleSearchMessage rsm);

	void addUserGroupRole(RoleSearchMessage rsm, UserGroupRoleBlock block);
	void delUserGroupRole(RoleSearchMessage rsm);


}
