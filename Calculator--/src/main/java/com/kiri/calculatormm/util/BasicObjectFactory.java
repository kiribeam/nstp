package com.kiri.calculatormm.util;

import java.util.LinkedList;

import com.kiri.calculatormm.exception.WrongVariableArgumentException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.CustomFunction;
import com.kiri.calculatormm.structure.data.BooleanData;
import com.kiri.calculatormm.structure.data.FalseData;
import com.kiri.calculatormm.structure.data.ListNode;
import com.kiri.calculatormm.structure.data.NumberData;
import com.kiri.calculatormm.structure.data.StringData;
import com.kiri.calculatormm.structure.data.TrueData;

public class BasicObjectFactory {
	public static CustomFunction createCustomFunction(String lambdaArgs, String expression) {
		LinkedList<String> args = new LinkedList<>();
		String[] parts = lambdaArgs.split(" ");
		boolean flag = false;
		for(int i=1; i<parts.length-1; i++) {
			if(parts[i].startsWith("..")) {
				if(i!=parts.length-2 || parts[i].length() == 2)
					throw new WrongVariableArgumentException(lambdaArgs);
				flag = true;
			} 
			args.add(parts[i]);
		}
		return new CustomFunction(expression, args, flag);
	}

	public static ListNode createListNode(BasicObject front, BasicObject behind) {
		ListNode node = new ListNode();
		node.setFront(front);
		node.setBehind(behind);
		return node;
	}
	
	public static NumberData createNumberData(double val) {
		return new NumberData(val);
	}
	
	public static StringData createStringData(String s) {
		return new StringData(s);
	}
	public static BooleanData createBooleanData(boolean b) {
		return b ? new TrueData() :new FalseData();
	}
}
