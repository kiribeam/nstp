package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;

public class NullJudgeFunction extends AssistFunction{

	public NullJudgeFunction() {
		super("null?", "( null? x) ", new LinkedList<String>(), false);
		super.getArgs().add("x");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject x = CalculatorCore.getEnvironmentVarFromStack("x", envStack);
		return x == null || !x.isTrue() ? 
				CalculatorCore.getEnvironmentVarFromStack("true", envStack)
				:CalculatorCore.getEnvironmentVarFromStack("false", envStack);
	}


}
