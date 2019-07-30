package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.OfflineLog;

public interface OfflineLogDao {
	List<OfflineLog> getLog(EcuSearchMessage esm);

	void addLog(OfflineLog log);
}
