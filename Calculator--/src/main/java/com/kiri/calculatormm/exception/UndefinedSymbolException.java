package com.kiri.calculatormm.exception;

public class UndefinedSymbolException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UndefinedSymbolException(String symbol) {
		super("Unknown symbol of: " + symbol);
	}

}
