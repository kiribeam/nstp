package kiri.nstp.web.service;

import java.util.List;
import java.util.Set;

import kiri.nstp.pojo.Permission;

public interface PermissionService {
	Set<Permission> getByRole(String role);
	Set<Permission> getByUsernameRole(String username);
	Set<Permission> getByUsernameGroupRole(String username);
	Set<Permission> getByUsername(String username);
	List<Permission> list();
	Permission getById(Integer id);
}
