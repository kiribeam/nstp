package kiri.nstp.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kiri.nstp.dao.RoleDao;
import kiri.nstp.pojo.Role;
import kiri.nstp.web.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;

	@Override
	public Set<Role> getRoleByUserName(String username) {
		return new HashSet<Role>(roleDao.getByUsername(username));
	}

	@Override
	public List<Role> list() {
		return roleDao.list();
	}

	@Override
	public Role getById(Integer id) {
		return roleDao.getById(id);
	}

}
