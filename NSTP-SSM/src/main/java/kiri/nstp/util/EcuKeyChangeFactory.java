package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.pojo.EcuKeyChange;

@Component
public class EcuKeyChangeFactory implements PojoFactory<EcuKeyChange>{
	@Override
	public EcuKeyChange genBlank() {
		return new EcuKeyChange();
	}
	
	public EcuKeyChange gen(String ecuid, Integer keyId, Integer count, String operator, String opValue, String m4, String keyFlag) {
		EcuKeyChange ekc = new EcuKeyChange();
		ekc.setEcuid(ecuid);
		ekc.setKeyId(keyId);
		ekc.setCount(count);
		ekc.setM4(m4);
		ekc.setOperator(operator);
		ekc.setOpValue(opValue);
		ekc.setKeyFlag(keyFlag);
		return ekc;
	}

}
