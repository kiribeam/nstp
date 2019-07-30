package com.kiri.calculatormm.test;

import org.junit.jupiter.api.Test;

import com.kiri.calculatormm.exception.BasicRuntimeException;
import com.kiri.calculatormm.util.ExpressionUtil;

public class TestTrans {
	@Test
	public void run() {
		//System.out.println("\a\b\c\d\e\f\g\h\i\j\k\@\$");
		String[] testcase = new String[] {
				"( )",
				"()",
				")(",
				"aaaa bbbbb",
				"aabb()",
				"aa ( bb cc\")",
				"(aa bb)",
				"( ( ( aa bb cc) aa)gg)",
				"( (( aa bb cc) dd) gg) bb",
				"( ( ( aaa \" gg())\")))",
				"( ( bb ) (aa \"aaaaaaa",
				"(( \" ggg()09()\" ))",
				"!#$%^^^^",
				"124.555 99"
		};
		for(String s: testcase) {
			try {
				System.out.println( "Cut input as :" +ExpressionUtil.formatExpression(s));
			} catch(BasicRuntimeException e) {
				System.out.println("Exception: " + e.getMessage());
			}
		}
	}

}
