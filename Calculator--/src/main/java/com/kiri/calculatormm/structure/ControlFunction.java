package com.kiri.calculatormm.structure;

import java.util.LinkedList;

public abstract class ControlFunction extends BasicFunction{
	
	public ControlFunction(String name, String expression, 
			LinkedList<String> args, boolean variable) {
		super("control", name, expression, args, variable);
	}
	

}
