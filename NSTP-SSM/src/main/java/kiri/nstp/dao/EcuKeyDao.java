package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuKey;

public interface EcuKeyDao {
	void initDefaultEcuKey(EcuKey ecuKey);
	List<EcuKey> getEcuKeys(EcuSearchMessage esm);
	List<EcuKey> getEcuKeysByList(List<Integer> list);
	EcuKey getEcuKeyById(Integer id);
	void updateEcuKey(EcuKey ecuKey);
	void delEcuKey(String ecuid);
}
