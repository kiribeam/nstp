package kiri.nstp.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kiri.nstp.dao.PermissionDao;
import kiri.nstp.pojo.Permission;
import kiri.nstp.web.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Set<Permission> getByRole(String role) {
		return new HashSet<Permission>(permissionDao.getByRole(role));
	}

	@Override
	public Set<Permission> getByUsernameRole(String username) {
		return new HashSet<Permission>(permissionDao.getByUsernameRole(username));
	}

	@Override
	public List<Permission> list() {
		return permissionDao.list();
	}

	@Override
	public Permission getById(Integer id) {
		return permissionDao.getById(id);
	}

	@Override
	public Set<Permission> getByUsernameGroupRole(String username) {
		return new HashSet<Permission>(permissionDao.getByUsernameGroupRole(username));
	}

	@Override
	public Set<Permission> getByUsername(String username) {
		return new HashSet<Permission>(permissionDao.getByUsername(username));
	}

}
