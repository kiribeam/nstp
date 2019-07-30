package com.kiri.calculatormm.exception;

public class ForceArgException  extends BasicRuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ForceArgException() {
		super("Can't force eval for wrong lazy function");
	}

}
