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

public abstract class OneMoreArgsIterCalculateFunction extends CalculateFunction{

	public OneMoreArgsIterCalculateFunction(String funcName) {
		super(funcName, "( " + funcName +" a ..b )", new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("a");
		args.add("b");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		double result = ((NumberData) CalculatorCore.getEnvironmentVarFromStack("a", envStack)).getVal();
		ListNode node = (ListNode) CalculatorCore.getEnvironmentVarFromStack("b", envStack);
		while(node != null) {
			result = NumberCalculateHelper.doubleArgsCalculate(this.getName(), result, 
					((NumberData) node.car()).getVal());
			node = (ListNode) node.cdr();
		}
		return BasicObjectFactory.createNumberData(result);
	}

}
