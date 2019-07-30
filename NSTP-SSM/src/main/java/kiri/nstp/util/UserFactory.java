package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.User;

@Component
public class UserFactory implements PojoFactory<User>{
	public User genBlank() {
		return new User();
	}
	public User gen(String username, String password, String email, String phone, Integer status) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		user.setStatus(status);
		user.setSalt(username);
		return user;
	}

}
