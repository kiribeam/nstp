package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.dto.EcuGroupBlock;

@Component
public class EcuGroupBlockFactory implements PojoFactory<EcuGroupBlock>{

	@Override
	public EcuGroupBlock genBlank() {
		return new EcuGroupBlock();
	}

}
