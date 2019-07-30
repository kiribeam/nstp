package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.data.NumberData;
import com.kiri.calculatormm.util.BasicObjectFactory;
import com.kiri.calculatormm.util.NumberCalculateHelper;

public abstract class SingleArgCalculateFunction extends CalculateFunction{

	public SingleArgCalculateFunction(String func) {
		super(func, "( " + func +  " x )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("x");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		double result = ((NumberData) CalculatorCore.getEnvironmentVarFromStack("x", envStack)).getVal();
		result = NumberCalculateHelper.singleArgCalculate(this.getName(), result);
		return BasicObjectFactory.createNumberData(result);
	}

}
