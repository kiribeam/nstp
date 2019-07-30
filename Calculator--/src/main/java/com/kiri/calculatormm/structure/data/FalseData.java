package com.kiri.calculatormm.structure.data;

public class FalseData extends BooleanData{
	
	public FalseData() {
		super("false", false);
	}
	@Override
	public boolean isTrue() {
		return false;
	}
	@Override
	public String toString() {
		return "false";
	}

}
