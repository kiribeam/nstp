package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.OfflineLog;

@Component
public class OfflineLogFactory implements PojoFactory<OfflineLog> {
	@Override
	public OfflineLog genBlank() {
		return new OfflineLog();
	}
	
	public OfflineLog gen(String ecuid, Integer keyId, Integer operation, String operator, String keyFlag) {
		OfflineLog of = new OfflineLog();
		of.setEcuid(ecuid);
		of.setKeyId(keyId);
		of.setOperation(operation);
		of.setOperator(operator);
		of.setKeyFlag(keyFlag);
		return of;
	}

}
