package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.CustomFunction;
import com.kiri.calculatormm.structure.data.ListNode;

public class OrFunction extends ControlFunction{

	public OrFunction() {
		super("Or", "( || first second ..others )", new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("first");
		args.add("second");
		args.add("others");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		CustomFunction cf = (CustomFunction) 
				CalculatorCore.getEnvironmentVarFromStack("first", envStack);
		BasicObject result = 
				CalculatorCore.eval(((CustomFunction) cf).getExpression(), envStack);
		if(result != null && result.isTrue()) return result;
		cf = (CustomFunction) CalculatorCore.getEnvironmentVarFromStack("second", envStack);
		result =
				CalculatorCore.eval(((CustomFunction) cf).getExpression(), envStack);
		if(result != null && result.isTrue()) return result;
		ListNode others = (ListNode)
				CalculatorCore.getEnvironmentVarFromStack("others", envStack);
		while(others != null) {
			cf = (CustomFunction) others.car();
			result = CalculatorCore.eval(cf.getExpression(), envStack);
			if(result != null && result.isTrue())
				return result;
			others = (ListNode) others.cdr();
		}
		return result;
	}
}
