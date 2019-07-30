package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.RefreshLog;

public interface RefreshLogDao {
	
	List<RefreshLog> getLog(EcuSearchMessage esm);
	
	void addLog(RefreshLog log);

}
