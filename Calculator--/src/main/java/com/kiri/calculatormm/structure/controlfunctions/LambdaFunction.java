package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.GeneralFunction;
import com.kiri.calculatormm.util.BasicObjectFactory;

public class LambdaFunction extends ControlFunction{

	public LambdaFunction() {
		super("lambda", "( lambda ( .. ) ( .. )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("args");
		args.add("expression");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		String args = 
				((GeneralFunction) CalculatorCore.getEnvironmentVarFromStack("args", envStack))
				.getExpression();
		String expression = 
				((GeneralFunction) CalculatorCore.getEnvironmentVarFromStack("expression", envStack))
				.getExpression();
		return BasicObjectFactory.createCustomFunction(args, expression);
	}
	

}
