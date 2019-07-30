package com.kiri.calculatormm.test;

import java.util.Scanner;

import org.junit.jupiter.api.Test;




public class TestInputEnter {
	@Test
	public void run() {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		String x=s;
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for(int i=0; i<len; i++) {
			if(s.charAt(i) == '\\') {
				if(i !=len-1) {
					i++;
					sb.append("\n");
				}
			}else {
				sb.append(s.charAt(i));
			}
		}
		System.out.println("input:" + sb.toString());
		

		x = x.replaceAll("\\\\n", "\n");
		System.out.println("X:"+ x);
		
		System.out.println("~~~~~~~~");
		String y = "\1\2";
		System.out.println("Y:"+y);
		y=y.replaceAll("\1", "\\1");
		System.out.println("Y:"+y);
		y=y.replaceAll("\2", "\\\\2");
		System.out.println("Y:"+y);
		
		sc.close();
	}
}
