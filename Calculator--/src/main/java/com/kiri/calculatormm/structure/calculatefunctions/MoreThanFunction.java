package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.data.NumberData;

public class MoreThanFunction extends CalculateFunction{

	public MoreThanFunction() {
		super(">", "( > a b )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		NumberData a = (NumberData) CalculatorCore.getEnvironmentVarFromStack("a", envStack);
		NumberData b = (NumberData) CalculatorCore.getEnvironmentVarFromStack("b", envStack);
		return a.getVal() > b.getVal() ? CalculatorCore.getEnvironmentVarFromStack("true", envStack)
				: CalculatorCore.getEnvironmentVarFromStack("false", envStack);
	}


}
