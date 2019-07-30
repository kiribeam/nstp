package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.data.NumberData;

public class NumberEqualsFunction extends CalculateFunction{

	public NumberEqualsFunction() {
		super("=", "( = a b )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
	}

	//Here use 0.000001 de
	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		NumberData a = (NumberData) CalculatorCore.getEnvironmentVarFromStack("a", envStack);
		NumberData b = (NumberData) CalculatorCore.getEnvironmentVarFromStack("b", envStack);
		return Math.abs(a.getVal() - b.getVal()) <= 0.000001? CalculatorCore.getEnvironmentVarFromStack("true", envStack)
				: CalculatorCore.getEnvironmentVarFromStack("false", envStack);
	}


}
