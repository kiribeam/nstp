package com.kiri.calculatormm.exception;

public class IntegralFormatException extends BasicRuntimeException{

	private static final long serialVersionUID = 1l;

	public IntegralFormatException(String message) {
		super("Integral format error : "+ message);
	}

}
