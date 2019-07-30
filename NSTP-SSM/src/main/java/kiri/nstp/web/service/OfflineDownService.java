package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.OfflineLog;

public interface OfflineDownService {

	List<OfflineLog> getLog(EcuSearchMessage esm);
	
	byte[] registKey(EcuSearchMessage esm, List<Integer> list, Integer authId, 
			Integer keyId, String keyFlag, String customKey);
	
	void verifyKey(EcuSearchMessage esm);


}
