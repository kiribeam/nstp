package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuGroupBlock;
import kiri.nstp.dto.EcuSearchMessage;

public interface EcuGroupDao {

	List<EcuGroupBlock> list(EcuSearchMessage esm);
	void add(EcuGroupBlock block);
	void del(Integer id);
	void delByEcuid(String ecuid);
	void delByGid(Integer gid);

}
