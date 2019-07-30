package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.exception.WrongExpressionException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;
import com.kiri.calculatormm.structure.CustomFunction;
import com.kiri.calculatormm.structure.GeneralFunction;

public class DefineFunction extends ControlFunction {

	public DefineFunction() {
		super("define", "( define  a  b )", new LinkedList<>(), false);
		LinkedList<String> args =  super.getArgs();
		args.add("name");
		args.add("basicObject");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		String name = ((GeneralFunction) 
				CalculatorCore.getEnvironmentVarFromStack("name", envStack))
				.getExpression();
		if(name.charAt(0)=='(' || name.charAt(0) == '\"' || (name.charAt(0)<='9' && name.charAt(0) >= '0'))
			throw new WrongExpressionException("define " + name);
		CustomFunction cf= (CustomFunction) CalculatorCore.getEnvironmentVarFromStack("basicObject", envStack);
		HashMap<String, BasicObject> funcEnv = envStack.pop();
		envStack.peek().put(name, CalculatorCore.eval(cf.getExpression(), envStack));
		envStack.push(funcEnv);
		return null;
	}

}
