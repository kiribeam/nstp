package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.core.CalculatorCore;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;

public class LazyFunction extends ControlFunction{

	public LazyFunction() {
		super("lazy", "( lazy a )", new LinkedList<String>(), false);
		LinkedList<String> args = super.getArgs();
		args.add("a");
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		return CalculatorCore.getEnvironmentVarFromStack("a", envStack);
	}

}
