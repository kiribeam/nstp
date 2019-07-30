package kiri.nstp.dao;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.DiagnosticLog;

public interface DiagnosticLogDao {

	List<DiagnosticLog> getLog(EcuSearchMessage esm);
	
	void addLog(DiagnosticLog log);

}
