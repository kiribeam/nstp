package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.data.StringData;

public class EvalFunction extends AssistFunction{

	public EvalFunction() {
		super("eval", "( eval string )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("string");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		StringData sd = (StringData) CalculatorCore.getEnvironmentVarFromStack("string", envStack);
		return CalculatorCore.eval(sd.toString(), envStack);

	}

}
