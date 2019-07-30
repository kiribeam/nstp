package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.DiagnosticLog;

@Component
public class DiagLogFactory implements PojoFactory<DiagnosticLog>{

	@Override
	public DiagnosticLog genBlank() {
		return new DiagnosticLog();
	}
	
	public DiagnosticLog gen(String ecuid, String operator) {
		DiagnosticLog log = new DiagnosticLog();
		log.setEcuid(ecuid);
		log.setOperator(operator);
		return log;
	}
	

}
