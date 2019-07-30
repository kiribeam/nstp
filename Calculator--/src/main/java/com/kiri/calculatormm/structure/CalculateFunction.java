package com.kiri.calculatormm.structure;

import java.util.LinkedList;

public abstract class CalculateFunction extends BasicFunction{
	
	public CalculateFunction(String name, String expression,
			LinkedList<String> args, boolean variable) {
		super("calculate", name, expression, args, variable);
	}

}
