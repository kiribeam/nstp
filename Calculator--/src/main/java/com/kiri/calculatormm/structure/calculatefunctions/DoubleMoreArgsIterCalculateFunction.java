package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.data.ListNode;
import com.kiri.calculatormm.structure.data.NumberData;
import com.kiri.calculatormm.util.BasicObjectFactory;
import com.kiri.calculatormm.util.NumberCalculateHelper;

public class DoubleMoreArgsIterCalculateFunction extends CalculateFunction{

	public DoubleMoreArgsIterCalculateFunction(String name) {
		super(name, "( "  + name + " a b ..c )", new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
		args.add("c");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		double result = ((NumberData) CalculatorCore.getEnvironmentVarFromStack("a", envStack)).getVal();
		result = NumberCalculateHelper.doubleArgsCalculate(this.getName(), result, 
				((NumberData) CalculatorCore.getEnvironmentVarFromStack("b", envStack)).getVal());
		ListNode node = (ListNode) CalculatorCore.getEnvironmentVarFromStack("c", envStack);
		while(node != null) {
			result = NumberCalculateHelper.doubleArgsCalculate(this.getName(), result, 
					((NumberData) node.car()).getVal());
			node = (ListNode) node.cdr();
		}
		return BasicObjectFactory.createNumberData(result);
	}

}
