package kiri.nstp.web.service;

import java.util.List;
import java.util.Set;

import kiri.nstp.pojo.Role;

public interface RoleService {
	Set<Role> getRoleByUserName(String username);
	List<Role> list();
	Role getById(Integer id);
}
