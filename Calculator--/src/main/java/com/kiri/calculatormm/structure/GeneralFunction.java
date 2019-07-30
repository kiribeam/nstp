package com.kiri.calculatormm.structure;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class GeneralFunction extends BasicObject{
	private final String expression;
	private final LinkedList<String> args;
	private final boolean variableArgs;
	
	public GeneralFunction(String type, String expression, 
			LinkedList<String> args, boolean variableArgs) {
		super(type);
		this.expression = expression;
		this.args = args;
		this.variableArgs = variableArgs;
	}
	

	public String getExpression() {
		return expression;
	}
	public LinkedList<String> getArgs() {
		return args;
	}
	public boolean isVariableArgs() {
		return variableArgs;
	}
	
	public abstract BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack);
		
	
	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(lambda ( ");
		for(String s: args) {
			sb.append(s);
			sb.append(" ");
		}
		sb.append(") ");
		sb.append(expression);
		sb.append(")");
		return sb.toString();
	}
}
