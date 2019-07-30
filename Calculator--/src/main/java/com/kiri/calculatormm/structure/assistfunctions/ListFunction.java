package com.kiri.calculatormm.structure.assistfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.AssistFunction;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.data.*;
import com.kiri.calculatormm.util.*;

public class ListFunction extends AssistFunction{

	public ListFunction() {
		super("list", "( list a ..b )", new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject bo = CalculatorCore.getEnvironmentVarFromStack("a", envStack);
		BasicObject bo2 = CalculatorCore.getEnvironmentVarFromStack("b", envStack);
		ListNode node = BasicObjectFactory.createListNode(bo, bo2);
		return node;
	}
}
