package kiri.nstp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kiri.nstp.dao.RolePermissionDao;
import kiri.nstp.dto.RolePermissionBlock;
import kiri.nstp.web.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService{
	
	@Value("${row10}")
	private Integer roleRow;
	
	@Autowired
	private RolePermissionDao rpDao;

	@Override
	public List<RolePermissionBlock> list(Integer page, String role) {
		PageHelper.startPage(page, roleRow);
		return rpDao.list(role);
	}
	

}
