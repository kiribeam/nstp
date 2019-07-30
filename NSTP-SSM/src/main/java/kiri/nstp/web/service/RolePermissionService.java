package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.RolePermissionBlock;

public interface RolePermissionService {
	List<RolePermissionBlock> list(Integer page, String role);

}
