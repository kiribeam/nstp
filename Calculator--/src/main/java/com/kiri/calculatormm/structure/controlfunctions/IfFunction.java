package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.GeneralFunction;

public class IfFunction extends ControlFunction {
	
	public IfFunction() {
		super("if", "( if ? a : b )", new LinkedList<>(), false);
		LinkedList<String> args =  super.getArgs();
		args.add("condition");
		args.add("first");
		args.add("second");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject bo = CalculatorCore.eval(
				((GeneralFunction) CalculatorCore.getEnvironmentVarFromStack("condition", envStack))
					.getExpression(), envStack);
		if(bo == null || !bo.isTrue()) 
			return CalculatorCore.eval(
					((GeneralFunction) CalculatorCore.getEnvironmentVarFromStack("second", envStack))
						.getExpression(), envStack);
		return CalculatorCore.eval(
				((GeneralFunction) CalculatorCore.getEnvironmentVarFromStack("first", envStack))
					.getExpression(), envStack);
	}
}
