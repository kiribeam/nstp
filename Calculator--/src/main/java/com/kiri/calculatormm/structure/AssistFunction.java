package com.kiri.calculatormm.structure;

import java.util.LinkedList;

public abstract class AssistFunction extends BasicFunction{

	public AssistFunction(String name, String expression, 
			LinkedList<String> args, boolean variable) {
		super("assist", name, expression, args, variable);
	}

}
