package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.dto.EcuSearchMessage;

@Component
public class EcuSearchMessageFactory implements PojoFactory<EcuSearchMessage>{

	@Override
	public EcuSearchMessage genBlank() {
		return new EcuSearchMessage();
	}
	

}
