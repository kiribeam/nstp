package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.PgpSearchMessage;
import kiri.nstp.pojo.UserPgp;

public interface PgpDao {
	List<UserPgp> getPgpList(PgpSearchMessage psm);
	UserPgp getPgpById(Integer id);
	
	void updatePgp(UserPgp up);
	
	void delPgp(Integer id);
	
	void createPgp(UserPgp up);

}
