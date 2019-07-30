package kiri.nstp.web.service.impl;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kiri.nstp.dao.UserDao;
import kiri.nstp.dao.UserGroupRoleDao;
import kiri.nstp.dao.UserRoleDao;
import kiri.nstp.dto.UserGroupRoleBlock;
import kiri.nstp.dto.UserSuite;
import kiri.nstp.exception.RegistFailException;
import kiri.nstp.pojo.User;
import kiri.nstp.util.UserFactory;
import kiri.nstp.util.UserGroupRoleBlockFactory;
import kiri.nstp.web.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserGroupRoleDao ugrDao;
	@Autowired
	private UserRoleDao urDao;
	
	@Autowired
	private UserFactory userFac;
	@Autowired
	private UserGroupRoleBlockFactory ugbFac;
	
	@Value("${statusUserValid}")
	private Integer userValid;
	@Value("${basicUserGroup}")
	private Integer gid;
	@Value("${basicUserRole}")
	private Integer rid;


	@Override
	public User getByName(String name) {
		return userDao.getByName(name);
	}

	@Override
	public List<User> list() {

		return userDao.list(null);
	}

	@Override
	@Transactional(rollbackFor= {Exception.class})
	public int regist(UserSuite us) {
		String enPass = new SimpleHash("md5", us.getPassword(), us.getUsername(), 2).toString();
		User user = userFac.gen(us.getUsername(), enPass, us.getMail(), null, userValid);
		try{
			userDao.add(user);
			UserGroupRoleBlock block = ugbFac.genBlank();
			block.setUsername(us.getUsername());
			block.setGid(gid);
			block.setRid(rid);
			ugrDao.add(block);
			urDao.add(block);
		}catch(Exception e) {
			throw new RegistFailException();
		}
		return 0;
	}

	@Override
	public void del(Integer id) {
		userDao.del(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public User getById(Integer id) {
		return userDao.getById(id);
	}
	

}
