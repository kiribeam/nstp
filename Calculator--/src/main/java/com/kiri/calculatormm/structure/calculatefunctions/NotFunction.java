package com.kiri.calculatormm.structure.calculatefunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;

public class NotFunction extends SingleArgCalculateFunction{

	public NotFunction() {
		super("!");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		BasicObject bo = CalculatorCore.getEnvironmentVarFromStack("x", envStack);
		return bo.isTrue() ? CalculatorCore.getEnvironmentVarFromStack("false", envStack)
				: CalculatorCore.getEnvironmentVarFromStack("true", envStack);
	}
}
