package kiri.nstp.util;

import org.springframework.stereotype.Component;

import kiri.nstp.dto.PgpSearchMessage;

@Component
public class PgpSearchMessageFactory implements PojoFactory<PgpSearchMessage>{

	@Override
	public PgpSearchMessage genBlank() {
		return new PgpSearchMessage();
	}
	
	public PgpSearchMessage gen(String fingerPrint, String username, Integer status) {
		PgpSearchMessage psm = new PgpSearchMessage();
		psm.setFingerPrint(fingerPrint);
		psm.setUsername(username);
		psm.setStatus(status);
		return psm;
	}

}
