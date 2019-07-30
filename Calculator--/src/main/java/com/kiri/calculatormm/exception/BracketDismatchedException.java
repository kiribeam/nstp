package com.kiri.calculatormm.exception;

public class BracketDismatchedException extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BracketDismatchedException(String message) {
		super("Bracket dismatched at : " + message);
	}
	


}
