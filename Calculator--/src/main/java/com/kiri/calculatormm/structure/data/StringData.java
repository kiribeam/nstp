package com.kiri.calculatormm.structure.data;

import com.kiri.calculatormm.structure.GeneralData;

public class StringData extends GeneralData{
	public final String val;

	//surrounded by " "
	public StringData(String string) {
		super("String");
		this.val= string;
	}
	
	@Override
	public String toString() {
		return val;
	}
	@Override
	public String getVal() {
		return val;
	}

}
