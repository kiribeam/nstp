package com.kiri.calculatormm.exception;

public class WrongExpressionException extends BasicRuntimeException{
	private static final long serialVersionUID = 1L;
	public WrongExpressionException(String message) {
		super("Expression wrong at : " + message);
	}
}
