package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuInfo;

public interface EcuInfoDao {
	
	List<EcuInfo> getEcuMessage(EcuSearchMessage esm);
	
	List<EcuInfo> getEcuByList(List<Integer> list);
	
	EcuInfo getEcuById(Integer id);
	EcuInfo getEcuByEcuid(String ecuid);
	
	void createEcu(EcuInfo ecu);
	
	void del(Integer id);
	
	void updateEcu(EcuInfo ecu);

}
