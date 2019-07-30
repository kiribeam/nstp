package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;

public interface UserGroupRoleDao {
	List<UserGroupRoleBlock> list(RoleSearchMessage rsm);
	void add(UserGroupRoleBlock block);
	void del(Integer id);

}
