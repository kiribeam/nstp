package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.pojo.Permission;

public interface PermissionDao {
	List<Permission> getByRole(String role);
	List<Permission> getByUsernameRole(String username);
	List<Permission> list();
	List<Permission> getByUsernameGroupRole(String username);
	List<Permission> getByUsername(String username);
	Permission getById(Integer id);

}
