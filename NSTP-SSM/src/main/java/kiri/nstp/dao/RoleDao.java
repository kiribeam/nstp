package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.pojo.Role;

public interface RoleDao {
	List<Role> getByUsername(String username);
	List<Role> list();
	Role getById(Integer id);
}
