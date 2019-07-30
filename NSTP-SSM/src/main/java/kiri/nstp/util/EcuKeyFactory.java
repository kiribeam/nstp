package kiri.nstp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kiri.nstp.pojo.EcuKey;

@Component
public class EcuKeyFactory implements PojoFactory<EcuKey>{
	@Value("${defaultKey}")
	private String defaultKey;

	@Override
	public EcuKey genBlank() {
		return new EcuKey();
	}

	public EcuKey gen(String ecuid, Integer keyId, Integer count, String keyFlag
			, String keyValue, Integer status){
		EcuKey ek = new EcuKey();
		ek.setEcuid(ecuid);
		ek.setCount(count);
		ek.setKeyId(keyId);
		ek.setKeyValue(keyValue);
		ek.setKeyFlag(keyFlag);
		ek.setStatus(status);
		return ek;
	}

}
