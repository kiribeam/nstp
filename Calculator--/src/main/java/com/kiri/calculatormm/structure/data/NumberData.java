package com.kiri.calculatormm.structure.data;

import com.kiri.calculatormm.structure.GeneralData;

public class NumberData extends GeneralData{
	
	private final double val;

	//first must be digit 
	public NumberData(double val) {
		super("number");
		this.val = val;
	}

	@Override
	public Double getVal() {
		return val;
	}
	
	@Override
	public String toString() {
		return val+"";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof NumberData)) return false;
		return Math.abs(((NumberData) o).getVal() - getVal()) <= 0.000001;
	}
}
