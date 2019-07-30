package com.kiri.calculatormm.exception;

public class UndefinedMethodException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UndefinedMethodException(String method) {
		super("Unknown method of: " + method);
	}


}
