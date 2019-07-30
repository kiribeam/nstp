package com.kiri.calculatormm.exception;

public class NotAFunctionException extends BasicRuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NotAFunctionException(String message) {
		super("Not a function : " + message);
	}

}
