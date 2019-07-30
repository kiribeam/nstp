package com.kiri.calculatormm.core;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.exception.ArgsNumberDismatchException;
import com.kiri.calculatormm.exception.NotAFunctionException;
import com.kiri.calculatormm.exception.UndefinedSymbolException;
import com.kiri.calculatormm.exception.WrongExpressionException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.GeneralFunction;
import com.kiri.calculatormm.util.BasicObjectFactory;
import com.kiri.calculatormm.util.ExpressionUtil;
import com.kiri.calculatormm.util.LinearTableUtil;

public class CalculatorCore {
	
	public static BasicObject eval(String expression, LinkedList<HashMap<String, BasicObject>> envStack) {
		LinkedList<String> parts = ExpressionUtil.formatExpression(expression);
		System.out.println("Expression: " + parts);
		if(parts.size() == 0) throw new WrongExpressionException(" nothing inputed!");

		if(parts.size() == 1) {
			return CalculatorCore.getEnvironmentVarFromStack(parts.get(0), envStack);
		}

		LinkedList<Object> evalStack = new LinkedList<>();
		LinkedList<BasicObject> tmpStack = new LinkedList<>();

		for(int i=0; i<parts.size(); i++) {
			if(parts.get(i).equals("(")) {
				evalStack.push("(");
				continue;
			}
			if(parts.get(i).equals(")")) {
				while(!evalStack.isEmpty() && 
						(evalStack.peek() == null || !evalStack.peek().toString().equals("("))) {
					tmpStack.push((BasicObject)evalStack.pop());
				}
				evalStack.pop();
				BasicObject tmpResult = apply(tmpStack, envStack);
				evalStack.push(tmpResult);
				tmpStack.clear();
				continue;
			}
			BasicObject bo = CalculatorCore.getEnvironmentVarFromStack(parts.get(i), envStack);
			if(evalStack.peek() != null && evalStack.peek().toString().equals("(") && bo.getType().equals("control")) {
				evalStack.pop();
				tmpStack.add(bo);
				int left = 1;
				StringBuilder sb = new StringBuilder();
				for(int j=i+1; j<parts.size(); j++) {
					if(parts.get(j).equals(")")) {
						left --;
						if(left == 0) {
							i=j;
							break;
						}
					}
					else if(parts.get(j).equals("("))
						left++;
					sb.append(" ");
					sb.append(parts.get(j));
					if(left == 1) {
						sb.deleteCharAt(0);
						tmpStack.add(BasicObjectFactory.createCustomFunction("( )", sb.toString()));
						sb = sb.delete(0, sb.length());
					}
				}
				evalStack.push(apply(tmpStack,envStack));
				System.out.println("EvalStack: " + evalStack);
			}else {
				evalStack.push(bo);
			}
		}
		return (BasicObject) evalStack.pop();
	}	
	
	public static BasicObject apply(LinkedList<BasicObject> tmpStack, 
			LinkedList<HashMap<String, BasicObject>> envStack) {
		if(tmpStack.isEmpty()) throw new WrongExpressionException(" empty brackets!");
		System.out.println("Apply: " + tmpStack);
		BasicObject o = tmpStack.pop();
		if(!(o instanceof GeneralFunction)) {
			throw new NotAFunctionException(o.toString());
		}
		GeneralFunction function = (GeneralFunction) o;
		HashMap<String, BasicObject> funcEnv = new HashMap<>();
		int size = function.getArgs().size();
		if(!function.isVariableArgs()) {
			if(tmpStack.size() != size) 
				throw new ArgsNumberDismatchException(function.toString());
			for(String arg: function.getArgs()) {
				funcEnv.put(arg, tmpStack.pop());
			}
		}else {
			System.out.println("variable");
			if(tmpStack.size() < size-1)
				throw new ArgsNumberDismatchException(function.toString());
			for(int i=0; i<size-1; i++) {
				funcEnv.put(function.getArgs().get(i), tmpStack.pop());
				System.out.println("tmpStack:" + tmpStack);
			}
			funcEnv.put(function.getArgs().get(size-1), LinearTableUtil.convertListToListNode(tmpStack));
		}
		System.out.println("FuncEnv: " + funcEnv);
		envStack.push(funcEnv);
		BasicObject result = function.exec(envStack);
		envStack.pop();
		return result;
	}
	
	
	public static BasicObject getEnvironmentVarFromStack(String name,
			LinkedList<HashMap<String, BasicObject>> envStack) {
		if(name.charAt(0) <= '9' && name.charAt(0) >= '0') {
			double result = Double.parseDouble(name);
			return BasicObjectFactory.createNumberData(result);
		}
		if(name.charAt(0) == '"') {
			return BasicObjectFactory.createStringData(name.substring(1,name.length()-1));
		}
		for(int i=0; i<envStack.size(); i++) {
			if(envStack.get(i).containsKey(name))
				return envStack.get(i).get(name);
		}
		throw new UndefinedSymbolException(name); 
	}	
	

}
