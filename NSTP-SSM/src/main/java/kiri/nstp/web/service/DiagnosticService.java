package kiri.nstp.web.service;

import java.util.List;
import java.util.Map;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.DiagnosticLog;

public interface DiagnosticService {
	
	List<DiagnosticLog> getLog(EcuSearchMessage esm);
	
	Map<String, String> diagnostic(EcuSearchMessage esm, String head, String rand);

}
