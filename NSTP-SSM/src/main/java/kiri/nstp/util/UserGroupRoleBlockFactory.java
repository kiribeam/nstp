package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.dto.UserGroupRoleBlock;

@Component
public class UserGroupRoleBlockFactory implements PojoFactory<UserGroupRoleBlock>{

	@Override
	public UserGroupRoleBlock genBlank() {
		return new UserGroupRoleBlock();
	}

}
