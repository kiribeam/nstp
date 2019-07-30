package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.RefreshLog;

@Component
public class RefreshLogFactory implements PojoFactory<RefreshLog>{

	@Override
	public RefreshLog genBlank() {
		return new RefreshLog();
	}
	
	public RefreshLog gen(String ecuid, Integer type, String operator) {
		RefreshLog log = new RefreshLog();
		log.setEcuid(ecuid);
		log.setOperator(operator);
		log.setType(type);
		return log;
	}

}
