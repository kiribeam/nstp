package com.kiri.calculatormm.structure.data;

import com.kiri.calculatormm.structure.GeneralData;

public abstract class BooleanData extends GeneralData{
	public final boolean val;

	public BooleanData(String type, boolean val) {
		super(type);
		this.val = val;
	}
	
	@Override
	public Boolean getVal() {
		return val;
	}
	

}
