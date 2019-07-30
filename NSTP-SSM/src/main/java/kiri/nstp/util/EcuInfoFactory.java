package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.EcuInfo;

@Component
public class EcuInfoFactory implements PojoFactory<EcuInfo>{
	@Override
	public EcuInfo genBlank() {
		return new EcuInfo();
	}
	public EcuInfo gen(String ecuid, String username, String mac, Integer status) {
		EcuInfo ecu = new EcuInfo();
		ecu.setEcuid(ecuid);
		ecu.setUser(username);
		ecu.setMacAddress(mac);
		ecu.setStatus(status);
		return ecu;
	}

}
