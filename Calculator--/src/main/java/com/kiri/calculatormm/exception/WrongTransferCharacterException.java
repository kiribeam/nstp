package com.kiri.calculatormm.exception;

public class WrongTransferCharacterException extends BasicRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public WrongTransferCharacterException(String message) {
		super("Wrong input String with : \\" + message);
	}


}
