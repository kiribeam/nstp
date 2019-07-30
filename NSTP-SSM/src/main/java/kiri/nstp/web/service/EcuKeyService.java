package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.pojo.EcuKeyChange;

public interface EcuKeyService {
	List<EcuKey> getEcuKeys(EcuSearchMessage esm);
	
	//void updateEcuKey(EcuKeyChange ekc);
	
	void initEcuKey(EcuKey ek);
	
	void delEcuKey(EcuKey ek);
	
	List<EcuKeyChange> getKeyChange(EcuSearchMessage esm);
	
	//void delKeyChange(EcuKey)
}
