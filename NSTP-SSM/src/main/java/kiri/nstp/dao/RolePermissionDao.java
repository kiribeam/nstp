package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.RolePermissionBlock;

public interface RolePermissionDao {
	
	List<RolePermissionBlock> list(String role);

}
