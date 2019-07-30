package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.UserPgp;

@Component
public class UserPgpFactory implements PojoFactory<UserPgp>{

	@Override
	public UserPgp genBlank() {
		return new UserPgp();
	}
	
	public UserPgp gen(String username, String fingerPrint, Integer status) {
		UserPgp up = new UserPgp();
		up.setUsername(username);
		up.setFingerPrint(fingerPrint);
		up.setStatus(status);
		return up;
		
	}

}
