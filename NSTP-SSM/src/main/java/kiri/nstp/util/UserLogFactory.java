package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.UserLog;

@Component
public class UserLogFactory implements PojoFactory<UserLog>{
	@Override
	public UserLog genBlank() {
		return new UserLog();
	}
	
	public UserLog gen(String username, Integer operation, String before, String after) {
		UserLog log = new UserLog();
		log.setAfter(after);
		log.setBefore(before);
		log.setOperation(operation);
		log.setUsername(username);
		return log;
	}

}
