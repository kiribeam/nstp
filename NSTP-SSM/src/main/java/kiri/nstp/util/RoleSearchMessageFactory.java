package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.dto.RoleSearchMessage;

@Component
public class RoleSearchMessageFactory implements PojoFactory<RoleSearchMessage>{

	@Override
	public RoleSearchMessage genBlank() {
		return new RoleSearchMessage();
	}
	

	public RoleSearchMessage gen(String username, String role, String userGroup) {
		RoleSearchMessage rsm = new RoleSearchMessage();
		rsm.setUserGroup(userGroup);
		rsm.setUsername(username);
		rsm.setRole(role);
		return rsm;
	}
}
