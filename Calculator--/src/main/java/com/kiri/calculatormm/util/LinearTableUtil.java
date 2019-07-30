package com.kiri.calculatormm.util;

import java.util.LinkedList;

import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.data.ListNode;

public class LinearTableUtil {
	//Be careful!!! here return null not nil!
	public static ListNode convertListToListNode(LinkedList<BasicObject> list) {
		if(list.isEmpty()) return null;
		ListNode head = new ListNode();
		head.setFront(list.pollLast());
		while(!list.isEmpty()) {
			ListNode tmp = head;
			head = new ListNode();
			head.setFront(list.pollLast());
			head.setBehind(tmp);
		}
		return head;
	}
	
	public static LinkedList<BasicObject> convertListNodeToList(ListNode node){
		LinkedList<BasicObject> list = new LinkedList<>();
		while(node != null) {
			list.add(node.car());
			node = (ListNode) node.cdr();
		}
		return list;
	}
}
