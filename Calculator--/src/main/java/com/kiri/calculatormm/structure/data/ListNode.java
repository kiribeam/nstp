package com.kiri.calculatormm.structure.data;

import com.kiri.calculatormm.structure.BasicObject;

public class ListNode extends BasicObject{
	
	private BasicObject front=null;
	private BasicObject behind=null;
	
	public ListNode() {
		super("list");
	}
	
	public BasicObject car() {
		return front;
	}
	
	public BasicObject cdr() {
		return behind;
	}
	
	@Override
	public boolean isList() {
		return true;
	}
	
	public BasicObject getFront() {
		return front;
	}

	public void setFront(BasicObject front) {
		this.front = front;
	}

	public BasicObject getBehind() {
		return behind;
	}

	public void setBehind(BasicObject behind) {
		this.behind = behind;
	}

	@Override
	public String toString() {
		return "(" + front + ", " + behind + ")";
	}
}
