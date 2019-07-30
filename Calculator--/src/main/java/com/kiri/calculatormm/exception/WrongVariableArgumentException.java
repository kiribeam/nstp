package com.kiri.calculatormm.exception;

public class WrongVariableArgumentException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public WrongVariableArgumentException(String message) {
		super("Wrong variable arguments : " + message);
		
	}

}
