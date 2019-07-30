package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.data.NumberData;

public class EqualFunction extends CalculateFunction{

	public EqualFunction() {
		super("eq?", "( eq a b )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject a =  CalculatorCore.getEnvironmentVarFromStack("a", envStack);
		BasicObject b = (NumberData) CalculatorCore.getEnvironmentVarFromStack("b", envStack);
		return a.equals(b) ? CalculatorCore.getEnvironmentVarFromStack("true", envStack)
				: CalculatorCore.getEnvironmentVarFromStack("false", envStack);
	}


}
