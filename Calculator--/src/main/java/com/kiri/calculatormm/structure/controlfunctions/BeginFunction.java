package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.CustomFunction;
import com.kiri.calculatormm.structure.data.ListNode;

public class BeginFunction extends ControlFunction{

	public BeginFunction() {
		super("begin", "( begin first ..others )", new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("first");
		args.add("others");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		CustomFunction cf = (CustomFunction) CalculatorCore.getEnvironmentVarFromStack("first", envStack);
		BasicObject result = CalculatorCore.eval(cf.getExpression(), envStack);
		ListNode node = (ListNode) CalculatorCore.getEnvironmentVarFromStack("others", envStack);
		while(node != null) {
			result = CalculatorCore.eval(
					((CustomFunction) node.car()).getExpression(), envStack);
			node = (ListNode) node.cdr();
		}
		return result;
	}
}
