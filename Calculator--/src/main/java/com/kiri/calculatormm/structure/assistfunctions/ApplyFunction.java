package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.GeneralFunction;
import com.kiri.calculatormm.structure.data.ListNode;
import com.kiri.calculatormm.util.LinearTableUtil;

public class ApplyFunction extends AssistFunction{

	public ApplyFunction() {
		super("apply", "( apply function list )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("function");
		args.add("list");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		GeneralFunction func = (GeneralFunction) 
				CalculatorCore.getEnvironmentVarFromStack("function", envStack);
		ListNode node = (ListNode)
				CalculatorCore.getEnvironmentVarFromStack("list", envStack);
		LinkedList<BasicObject> tmpStack = LinearTableUtil.convertListNodeToList(node);
		tmpStack.push(func);
		return CalculatorCore.apply(tmpStack, envStack);
	}

}
