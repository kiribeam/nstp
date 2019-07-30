package com.kiri.calculatormm.structure;

import java.util.LinkedList;

public abstract class BasicFunction extends GeneralFunction{

	public final String name;
	
	public BasicFunction(String type, String name, String expression,
			LinkedList<String> args, boolean variable) {
		super(type, expression, args, variable);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.getExpression();
	}
	

}
