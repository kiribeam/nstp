package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.exception.IntegralFormatException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CalculateFunction;
import com.kiri.calculatormm.structure.GeneralFunction;
import com.kiri.calculatormm.structure.data.ListNode;
import com.kiri.calculatormm.structure.data.NumberData;
import com.kiri.calculatormm.util.BasicObjectFactory;
import com.kiri.calculatormm.util.LinearTableUtil;
import com.kiri.calculatormm.util.NumberCalculateHelper;

public class IntegralFunction extends CalculateFunction{

	public IntegralFunction() {
		super("integral", "( integral function ..vars )",
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
			throw new IntegralFormatException(" Can't use variable function!");
		int argsNumber = func.getArgs().size();
		ListNode node = (ListNode)
				CalculatorCore.getEnvironmentVarFromStack("vars", envStack);
		LinkedList<BasicObject> vars = LinearTableUtil.convertListNodeToList(node);
		if(vars.size() != argsNumber*2)
			throw new IntegralFormatException(" Integral boundary args' number not match!\n"+
					"Need: " + argsNumber*2 + " ==> input: " + vars.size());
		String error = "Integral vars format must like:\n"
				+ "number number f(x) f(x) f(x, y) f(x, y) ...\n"
				+ "And be careful the boundary must match the sequence of input function args";
		for(int i=0; i<argsNumber*2; i++) {
			if(i/2 == 0) {
				if(vars.get(i) == null || !(vars.get(i) instanceof NumberData))
					throw new IntegralFormatException(error);
			}else {
				if(vars.get(i) == null
						|| !(vars.get(i) instanceof GeneralFunction)
						|| (((GeneralFunction) vars.get(i)).getArgs().size() != i/2)) {
					throw new IntegralFormatException(error + "\n at: " + vars.get(i));
				}
			}
		}
		double result = integral(func, vars, new LinkedList<BasicObject>(), envStack);
		return BasicObjectFactory.createNumberData(result);

	}

	private double integral( GeneralFunction function,LinkedList<BasicObject> vars,
			LinkedList<BasicObject> curList,
			LinkedList<HashMap<String, BasicObject>> envStack) {
		if(vars.size() == 0) {
			LinkedList<BasicObject> tmpStack = new LinkedList<>(curList);
			tmpStack.push(function);
			NumberData result = (NumberData) CalculatorCore.apply(tmpStack, envStack);
			return result.getVal();
		}
		double from;
		double to;
		BasicObject fromObject = vars.pop();
		BasicObject toObject = vars.pop();
		if(curList.isEmpty()) {
			from = ((NumberData) fromObject).getVal();
			to = ((NumberData) toObject).getVal();
		}else {
			LinkedList<BasicObject> tmpStack = new LinkedList<>(curList);
			tmpStack.push(fromObject);
			from = ((NumberData) CalculatorCore.apply(tmpStack, envStack)).getVal();
			tmpStack.clear();
			tmpStack.addAll(curList);
			tmpStack.push(toObject);
			to = ((NumberData) CalculatorCore.apply(tmpStack, envStack)).getVal();
		}
		int step = ((NumberData) (CalculatorCore.getEnvironmentVarFromStack("@intSteps", envStack))).getVal().intValue();
		double[] results = new double[step];
		for(int i=0; i<step; i++) {
			NumberData curData = BasicObjectFactory.createNumberData((to-from)/step*i+from);
			curList.add(curData);
			results[i] = integral(function, vars, curList, envStack);
			curList.pollLast();
		}
		vars.push(toObject);
		vars.push(fromObject);
		return (to-from)/step * NumberCalculateHelper.arraySum(results);
	}
}
