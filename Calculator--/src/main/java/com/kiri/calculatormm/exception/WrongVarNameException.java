package com.kiri.calculatormm.exception;

public class WrongVarNameException extends BasicRuntimeException {
	private static final long serialVersionUID = 1L;

	public WrongVarNameException(String message) {
		super("Wrong var name : " + message);
		
	}

}
