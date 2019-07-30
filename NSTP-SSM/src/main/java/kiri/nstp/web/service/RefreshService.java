package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.RefreshLog;

public interface RefreshService {
	
	byte[] generateSignedFile(byte[] file, String header, String app, String signInfo, EcuSearchMessage esm);

	List<RefreshLog> getLog(EcuSearchMessage esm);
	

}
