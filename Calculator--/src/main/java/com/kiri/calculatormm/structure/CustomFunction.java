package com.kiri.calculatormm.structure;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;

public class CustomFunction extends GeneralFunction{
	
	public CustomFunction(String expression, LinkedList<String> args, boolean variable) {
		super("lambda", expression, args, variable);
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		return CalculatorCore.eval(getExpression(), envStack);
	}

}
