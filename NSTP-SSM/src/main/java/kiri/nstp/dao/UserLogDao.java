package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.UserSearchMessage;
import kiri.nstp.pojo.UserLog;

public interface UserLogDao {
	List<UserLog> getLog(UserSearchMessage usm);
	void addLog(UserLog log);
}
