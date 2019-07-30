package com.kiri.calculatormm.util;

import java.lang.reflect.Method;

import com.kiri.calculatormm.exception.ArraySumException;
import com.kiri.calculatormm.exception.UndefinedMethodException;

public class NumberCalculateHelper {
	public static double singleArgCalculate(String func, double arg){
		try {
			Method m = Math.class.getMethod(func, double.class);
			double result = (Double) m.invoke(null, arg);
			return result;
		}catch(Exception e) {
			throw new UndefinedMethodException(func);
		}
	}
	
	public static double doubleArgsCalculate(String func, double arg1, double arg2) {
		switch(func) {
		case "+" :
			return arg1 + arg2;
		case "-" :
			return arg1 - arg2;
		case "*" :
			return arg1 * arg2;
		case "/" :
			return arg1 / arg2;
		}
		try {
			Method m = Math.class.getMethod(func, double.class, double.class);
			double result = (Double) m.invoke(null, arg1, arg2);
			return result;
		}catch(Exception e) {
			throw new UndefinedMethodException(func);
		}
	}
	
	public static double arraySum(double[] array) {
		int length = array.length;
		if(length == 0) return 0;
		int step = length;
		int count = 0;
		while(step != 1) {
			if(step%2 == 1) {
				array[0] += array[step-1];
				step --;
			}
			for(int i=0; i<step/2; i++) {
				array[i] += array[step -1-i];
				if(array[i] == Double.NaN) {
					array[i] = 0;
					count++;
					if(count > step/8) throw new ArraySumException(" Too many NaN values");
				}
			}
			step /= 2;
		}
		return array[0];
	}
	public static void main(String[] args){
		System.out.println("GG"+singleArgCalculate("sin", Math.PI/6));
		
		System.out.println("DD:" + doubleArgsCalculate("max", 100, 214));
	}
}
