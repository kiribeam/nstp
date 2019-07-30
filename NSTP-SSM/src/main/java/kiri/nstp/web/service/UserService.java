package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.UserSuite;
import kiri.nstp.pojo.User;

public interface UserService {
	User getByName(String name);
	User getById(Integer id);
	List<User> list();
	
	int regist(UserSuite us);
	void del(Integer id);
	void update(User user);

}
