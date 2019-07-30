package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.UserSearchMessage;
import kiri.nstp.pojo.User;

public interface UserDao {
	List<User> list(UserSearchMessage usm);
	User getById(Integer id);
	User getByName(String username);
	
	void del(Integer id);
	void update(User user);
	void add(User user);
}
