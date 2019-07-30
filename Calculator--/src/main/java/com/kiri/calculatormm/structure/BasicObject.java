package com.kiri.calculatormm.structure;

public abstract class BasicObject {
	public final String type;
	
	public BasicObject(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	

	public boolean isData() {
		return false;
	}
	public boolean isFunction() {
		return false;
	}
	public boolean isList() {
		return false;
	}
	public boolean isTrue() {
		return true;
	}
}
