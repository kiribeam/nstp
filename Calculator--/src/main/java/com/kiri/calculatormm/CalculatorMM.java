package com.kiri.calculatormm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.core.CalculatorInitializor;
import com.kiri.calculatormm.exception.BasicRuntimeException;
import com.kiri.calculatormm.exception.ExitProgramException;
import com.kiri.calculatormm.structure.BasicObject;

public class CalculatorMM {
	public static void main(String[] args) {
		LinkedList<HashMap<String, BasicObject>> envStack = 
				CalculatorInitializor.initEnv();
		Scanner sc  = new Scanner(System.in);
		String programLine = "";
		while(true) {
			String line = sc.nextLine();
			if(line.endsWith(" \\")) {
				programLine = programLine + line.substring(0, line.length()-1);
				continue;
			}else {
				programLine = programLine + line;
				line = programLine;
				programLine = "";
			}
			try {
				System.out.println("Eval : " + CalculatorCore.eval(
						line.replaceAll("\\t", " ").replaceAll("\\n", " "), envStack));
			}catch(ExitProgramException e) {
				System.out.println(e.getMessage());
				break;
			}catch (BasicRuntimeException e) {
				System.out.println("Err:" + e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		sc.close();
	}
}
