package com.kiri.calculatormm.exception;

public class ArgsNumberDismatchException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ArgsNumberDismatchException(String func) {
		super("Argument's number is not matches :");
	}

}
