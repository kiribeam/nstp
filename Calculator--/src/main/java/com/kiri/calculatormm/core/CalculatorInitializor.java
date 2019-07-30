package com.kiri.calculatormm.core;

import java.util.HashMap;
import java.util.LinkedList;

import com.kiri.calculatormm.structure.BasicObject;
import com.kiri.calculatormm.structure.assistfunctions.ApplyFunction;
import com.kiri.calculatormm.structure.assistfunctions.CarFunction;
import com.kiri.calculatormm.structure.assistfunctions.CdrFunction;
import com.kiri.calculatormm.structure.assistfunctions.ConstructionFunction;
import com.kiri.calculatormm.structure.assistfunctions.EqualFunction;
import com.kiri.calculatormm.structure.assistfunctions.EvalFunction;
import com.kiri.calculatormm.structure.assistfunctions.ListFunction;
import com.kiri.calculatormm.structure.assistfunctions.NullJudgeFunction;
import com.kiri.calculatormm.structure.calculatefunctions.DerivativeFunction;
import com.kiri.calculatormm.structure.calculatefunctions.DoubleMoreArgsIterCalculateFunction;
import com.kiri.calculatormm.structure.calculatefunctions.IntegralFunction;
import com.kiri.calculatormm.structure.calculatefunctions.LessEqualThanFunction;
import com.kiri.calculatormm.structure.calculatefunctions.LessThanFunction;
import com.kiri.calculatormm.structure.calculatefunctions.MinusFunction;
import com.kiri.calculatormm.structure.calculatefunctions.MoreEqualThanFunction;
import com.kiri.calculatormm.structure.calculatefunctions.MoreThanFunction;
import com.kiri.calculatormm.structure.calculatefunctions.NotFunction;
import com.kiri.calculatormm.structure.calculatefunctions.NumberEqualsFunction;
import com.kiri.calculatormm.structure.calculatefunctions.OneMoreArgsIterCalculateFunction;
import com.kiri.calculatormm.structure.calculatefunctions.SingleArgCalculateFunction;
import com.kiri.calculatormm.structure.controlfunctions.AndFunction;
import com.kiri.calculatormm.structure.controlfunctions.BeginFunction;
import com.kiri.calculatormm.structure.controlfunctions.DefineFunction;
import com.kiri.calculatormm.structure.controlfunctions.ExitFunction;
import com.kiri.calculatormm.structure.controlfunctions.IfFunction;
import com.kiri.calculatormm.structure.controlfunctions.LambdaFunction;
import com.kiri.calculatormm.structure.controlfunctions.OrFunction;
import com.kiri.calculatormm.structure.controlfunctions.ResetFunction;
import com.kiri.calculatormm.util.BasicObjectFactory;

public class CalculatorInitializor {
	
	private static double precision = 0.00001;
	private static int integralSteps = 512;
	private static double derivativePrecision = 0.001;
	
	public static LinkedList<HashMap<String, BasicObject>> initEnv(){
		LinkedList<HashMap<String, BasicObject>> envStack = new LinkedList<>();
		HashMap<String, BasicObject> globalEnv = new HashMap<>();
		envStack.push(globalEnv);

		globalEnv.put("true", BasicObjectFactory.createBooleanData(true));
		globalEnv.put("false", BasicObjectFactory.createBooleanData(false));
		globalEnv.put("PI", BasicObjectFactory.createNumberData(Math.PI));
		globalEnv.put("E", BasicObjectFactory.createNumberData(Math.E));
		globalEnv.put("NaN", BasicObjectFactory.createNumberData(Double.NaN));
		globalEnv.put("null", null);

		globalEnv.put("@intSteps", BasicObjectFactory.createNumberData(integralSteps));
		globalEnv.put("@precision", BasicObjectFactory.createNumberData(precision));
		globalEnv.put("@derPrecision", BasicObjectFactory.createNumberData(derivativePrecision));

		globalEnv.put("exit", new ExitFunction());
		globalEnv.put("reset", new ResetFunction());

		globalEnv.put("&&", new AndFunction());
		globalEnv.put("||", new OrFunction());
		globalEnv.put("begin", new BeginFunction());
		globalEnv.put("define", new DefineFunction());
		globalEnv.put("let", new DefineFunction());
		globalEnv.put("if", new IfFunction());
		globalEnv.put("lambda", new LambdaFunction());
		//globalEnv.put("lazy", new LazyFunction());
		//globalEnv.put("force", new ForceFunction());
		
		
		globalEnv.put("apply", new ApplyFunction());
		globalEnv.put("car", new CarFunction());
		globalEnv.put("cdr", new CdrFunction());
		globalEnv.put("cons", new ConstructionFunction());
		globalEnv.put("eval", new EvalFunction());
		globalEnv.put("list", new ListFunction());

		
		globalEnv.put("eq?", new EqualFunction());
		globalEnv.put("null?", new NullJudgeFunction());
		
		
		globalEnv.put("abs", new SingleArgCalculateFunction("abs") {});
		globalEnv.put("asin", new SingleArgCalculateFunction("asin") {});
		globalEnv.put("atan", new SingleArgCalculateFunction("atan") {});
		globalEnv.put("cbrt", new SingleArgCalculateFunction("cbrt") {});
		globalEnv.put("cos", new SingleArgCalculateFunction("cos") {});
		globalEnv.put("cosh", new SingleArgCalculateFunction("cosh") {});
		globalEnv.put("exp", new SingleArgCalculateFunction("exp") {});
		globalEnv.put("lg", new SingleArgCalculateFunction("log10") {});
		globalEnv.put("ln", new SingleArgCalculateFunction("log") {});
		globalEnv.put("toRadians", new SingleArgCalculateFunction("toRadians") {});
		globalEnv.put("toDegrees", new SingleArgCalculateFunction("toDegrees") {});
		globalEnv.put("sin", new SingleArgCalculateFunction("sin") {});
		globalEnv.put("sinh", new SingleArgCalculateFunction("sinh") {});
		globalEnv.put("sqrt", new SingleArgCalculateFunction("sqrt") {});
		globalEnv.put("tan", new SingleArgCalculateFunction("tan") {});
		globalEnv.put("tanh", new SingleArgCalculateFunction("tanh") {});
		
		
		globalEnv.put("/", new DoubleMoreArgsIterCalculateFunction("/") {});
		globalEnv.put("*", new DoubleMoreArgsIterCalculateFunction("*") {});
		globalEnv.put("pow", new DoubleMoreArgsIterCalculateFunction("pow") {});

		
		globalEnv.put("+", new OneMoreArgsIterCalculateFunction("+") {});
		globalEnv.put("max", new OneMoreArgsIterCalculateFunction("max") {});
		globalEnv.put("min", new OneMoreArgsIterCalculateFunction("min") {});
		
		globalEnv.put("-", new MinusFunction());
		globalEnv.put("!", new NotFunction());
		globalEnv.put("=", new NumberEqualsFunction());
		globalEnv.put(">", new MoreThanFunction());
		globalEnv.put(">=", new MoreEqualThanFunction());
		globalEnv.put("<", new LessThanFunction());
		globalEnv.put("<=", new LessEqualThanFunction());
		globalEnv.put("integral", new IntegralFunction());
		globalEnv.put("derivative", new DerivativeFunction());

		//avoid destroy basic functions
		envStack.push(new HashMap<String, BasicObject>());
		return envStack;
	}

	public static double getPrecision() {
		return precision;
	}

	public static void setPrecision(double precision) {
		CalculatorInitializor.precision = precision;
	}

	public static int getIntegralSteps() {
		return integralSteps;
	}

	public static void setIntegralSteps(int integralSteps) {
		CalculatorInitializor.integralSteps = integralSteps;
	}

	public static double getDerivativePrecision() {
		return derivativePrecision;
	}

	public static void setDerivativePrecision(double derivativePrecision) {
		CalculatorInitializor.derivativePrecision = derivativePrecision;
	}

}
