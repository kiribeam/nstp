package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.util.BasicObjectFactory;

public class ConstructionFunction extends AssistFunction {

	public ConstructionFunction() {
		super("cons", "( cons front behind )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("front");
		args.add("behind");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject front = CalculatorCore.getEnvironmentVarFromStack("front", envStack);
		BasicObject behind = CalculatorCore.getEnvironmentVarFromStack("behind", envStack);
		return BasicObjectFactory.createListNode(front, behind);
	}

}
