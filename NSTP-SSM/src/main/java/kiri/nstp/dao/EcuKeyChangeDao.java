package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuKeyChange;

public interface EcuKeyChangeDao {
	List<EcuKeyChange> getList(EcuSearchMessage esm);
	EcuKeyChange getById(Integer id);
	
	void del(EcuKeyChange ekc);
	
	void delFollowEcu(String ecuid);
	
	void add(EcuKeyChange ekc);

}
