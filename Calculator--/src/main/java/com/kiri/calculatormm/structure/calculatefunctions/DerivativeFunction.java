package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.core.CalculatorInitializor;
import com.kiri.calculatormm.exception.DerivativeFormatException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.GeneralFunction;
import com.kiri.calculatormm.structure.data.ListNode;
import com.kiri.calculatormm.structure.data.NumberData;
import com.kiri.calculatormm.util.BasicObjectFactory;
import com.kiri.calculatormm.util.LinearTableUtil;

public class DerivativeFunction extends CalculateFunction{

	public DerivativeFunction() {
		super("deriavative", ("( deriavative function ..vars )"), 
				new LinkedList<String>(), true);
		LinkedList<String> args = super.getArgs();
		args.add("function");
		args.add("vars");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		GeneralFunction func = (GeneralFunction)
				CalculatorCore.getEnvironmentVarFromStack("function", envStack);
		if(func.isVariableArgs())
			throw new DerivativeFormatException(" Can't use variable function!");
		int argsNumber = func.getArgs().size();
		ListNode node = (ListNode)
				CalculatorCore.getEnvironmentVarFromStack("vars", envStack);
		LinkedList<BasicObject> vars = LinearTableUtil.convertListNodeToList(node);
		if(vars.size() != argsNumber*2)
			throw new DerivativeFormatException(" Derivative args' number not match!\n"+
					"Need: " + argsNumber*2 + " ==> input: " + vars.size());
		String error = "Derivative vars format must like:\n"
				+ "arg1 scale1 arg2 scale2 ...\n"
				+ "And be careful to match the sequence of input function args";
		double derPrecision = ((NumberData) CalculatorCore.getEnvironmentVarFromStack("@derPrecision", envStack)).getVal();
		LinkedList<BasicObject> tmpStack = new LinkedList<>();
		for(int i=0; i<argsNumber; i++) {
			if(vars.peek() == null || !(vars.peek() instanceof NumberData)) {
				throw new DerivativeFormatException(error + "\nAll the Args must be a number");
			}
			tmpStack.add(vars.pop());
		}
		LinkedList<BasicObject> stack2 = new LinkedList<>(tmpStack);
		stack2.push(func);
		double frontValue = ((NumberData) CalculatorCore.apply(stack2, envStack)).getVal();
		stack2.clear();
		double scale = 0;
		for(int i=0; i<argsNumber; i++) {
			if(vars.get(i) == null || !(vars.get(i) instanceof NumberData)) {
				throw new DerivativeFormatException(error + "\nAll the Args must be a number");
			}
			scale += Math.pow(((NumberData)vars.get(i)).getVal(), 2);
		}
		scale = Math.sqrt(scale);
		if(Math.abs(scale - 0) <= CalculatorInitializor.getPrecision()) throw new DerivativeFormatException("Must have a valid direction except 0");
		for(int i=0; i<argsNumber; i++) {
			double d = ((NumberData) tmpStack.get(i)).getVal();
			d += frontValue * derPrecision * ((NumberData) vars.get(i)).getVal() / scale;
			stack2.add(BasicObjectFactory.createNumberData(d));
		}
		stack2.push(func);
		double behindValue = ((NumberData) CalculatorCore.apply(stack2, envStack)).getVal();
		return BasicObjectFactory.createNumberData((behindValue-frontValue)/(derPrecision*frontValue));
	}
}
