package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.CustomFunction;

public class ForceFunction extends ControlFunction{

	public ForceFunction() {
		super("force", "( force func )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("func");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		CustomFunction cf = (CustomFunction) CalculatorCore.getEnvironmentVarFromStack("func", envStack);
		return CalculatorCore.eval(
				((CustomFunction)CalculatorCore.eval( cf.getExpression(), envStack)).getExpression(),
				envStack);
	}
}
