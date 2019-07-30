package com.kiri.calculatormm.exception;

public class BasicRuntimeException extends RuntimeException {

	private String message;
	private static final long serialVersionUID = 1L;
	public BasicRuntimeException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
