package com.kiri.calculatormm.util;

import java.util.LinkedList;

import com.kiri.calculatormm.exception.BracketDismatchedException;
import com.kiri.calculatormm.exception.WrongExpressionException;
import com.kiri.calculatormm.exception.WrongTransferCharacterException;

public class ExpressionUtil {

	public static LinkedList<String> formatExpression(String input) {
		LinkedList<String> expression = new LinkedList<>();
		input = input.trim();
		int head = 0;
		int left = 0;
		boolean stringFlag = false;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<input.length(); i++) {
			switch(input.charAt(i)) {
			case ' ':
				if(!stringFlag) {
					if(head != i) expression.add(input.substring(head, i));
					head = i+1;
				}else {
					sb.append(input.charAt(i));
				}
				break;

			case '(':
				if(head == i) {
					head++;
					left++;
					expression.add("(");
				}else if(!stringFlag) {
					throw new WrongExpressionException(cutString(input, i, 4));
				}else {
					sb.append(input.charAt(i));
				}
				break;

			case ')':
				if(!stringFlag) {
					left --;
					if(left <0)
						throw new BracketDismatchedException(cutString(input, i, 4)); 
					if(left == 0 && i!=input.length()-1)
						throw new WrongExpressionException(cutString(input, i, 4));
					if(head != i)
						expression.add(input.substring(head, i));
					if(i!=input.length()-1 && input.charAt(i+1) != ' ' && input.charAt(i+1) != ')')
						throw new WrongExpressionException(cutString(input, i, 4));
					expression.add(")");
					head = i+1;
				}else {
					sb.append(input.charAt(i));
				}
				break;
				
			case '"':
				if(!stringFlag) {
					if(i!=0 && input.charAt(i-1) != ' ' && input.charAt(i-1) != '(')
						throw new WrongExpressionException(cutString(input, i, 4));
					stringFlag = true;
					sb.append("\"");
				}else {
					if(i!=input.length()-1 && input.charAt(i+1) != ' ' && input.charAt(i+1) != ')')
						throw new WrongExpressionException(cutString(input, i, 4));
					sb.append("\"");
					stringFlag = false;
					expression.add(sb.toString());
					sb = new StringBuilder();
					head = i+1;
				}
				break;
			case '\\':
				if(stringFlag) {
					i++;
					if(i==input.length()) throw new WrongExpressionException("endless string");
					sb.append(trans(input.charAt(i)));
				}
				break;
			default:
				if(stringFlag) sb.append(input.charAt(i));
			}
		}
		if(stringFlag) throw new WrongExpressionException("endless string");
		if(head != input.length()) expression.add(input.substring(head));
		if(left != 0) throw new BracketDismatchedException("dismatched )");
		return expression;
	}
	private static String cutString(String s, int i, int step) {
		int len = s.length();
		return s.substring(i-step <0 ? 0 : i-step, i+step >len? len:i+step);
	}
	private static char trans(char c) {
		switch(c) {
		case 'b' :
			return '\b';
		case 't' :
			return '\t';
		case 'n' :
			return '\n';
		case 'f' :
			return '\f';
		case 'r' :
			return '\r';
		case '"' :
			return '"';
		case '\'':
			return '\'';
		case '\\':
			return '\\';
		default :
			throw new WrongTransferCharacterException(c+"");
		}
	}
	
}
