package com.kiri.calculatormm.structure;

public abstract class GeneralData extends BasicObject{
	
	public GeneralData(String type) {
		super(type);
	}
	
	@Override
	public boolean isData() {
		return true;
	}
	
	public abstract Object getVal();
}
