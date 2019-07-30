package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.data.ListNode;

public class CarFunction extends AssistFunction{

	public CarFunction() {
		super("car", "( car list )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("list");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		return ((ListNode) CalculatorCore.getEnvironmentVarFromStack("list", envStack)).car();
	}


}
