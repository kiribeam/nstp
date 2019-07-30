package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.exception.ExitProgramException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;

public class ExitFunction extends ControlFunction{

	public ExitFunction() {
		super("exit", "( exit )", new LinkedList<String>(), false);
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		throw new ExitProgramException();
	}

}
