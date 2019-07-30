package com.kiri.calculatormm.structure.controlfunctions;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.exception.ResetEnvironmentException;
import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.ControlFunction;

public class ResetFunction extends ControlFunction{

	public ResetFunction() {
		super("reset", "( reset )", new LinkedList<String>() , false);
	}

	@Override
	public BasicObject exec(LinkedList<HashMap<String, BasicObject>> envStack) {
		while(envStack.size()>1) {
			envStack.pop();
		}
		envStack.add(new HashMap<String, BasicObject>());
		throw new ResetEnvironmentException();
	}

}
