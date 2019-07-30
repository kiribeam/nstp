package com.kiri.calculatormm.exception;

public class ExitProgramException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExitProgramException() {
		super("Goodbye!");
	}

}
