package com.paracamplus.ilp4.ilp4tme10.interpreter;

import java.util.Set;

import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
//import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitorTME10;

public class InterpreterTME10
	extends com.paracamplus.ilp4.interpreter.Interpreter
	implements IASTvisitorTME10<Object, ILexicalEnvironment, EvaluationException> {

	protected Set<String> globals;
	
	public InterpreterTME10(
			IGlobalVariableEnvironment globalVariableEnvironment,
			IOperatorEnvironment operatorEnvironment,
			IClassEnvironment classEnvironment) {
		super(globalVariableEnvironment, operatorEnvironment, classEnvironment);
	}

	@Override
	public Object visit(IASTprogram iast, ILexicalEnvironment lexenv)
			throws EvaluationException {

		globals = (new GlobalVariableCollectorInterpreterTME10()).visit(iast).getGlobals();

		return super.visit(iast, lexenv);
	}

	@Override
	public Object visit(IASTexists iast, ILexicalEnvironment env)
			throws EvaluationException {
		IASTvariable var = iast.getVariable();

		return globals.contains(var.getName()) ||

				getGlobalVariableEnvironment().getGlobalVariableValue(var.getName()) != null ||
				env.isPresent(iast.getVariable());
	}

	@Override
	public Object visit(IASTdefined iast, ILexicalEnvironment env)
			throws EvaluationException {
		IASTvariable var = iast.getVariable();

		return getGlobalVariableEnvironment().getGlobalVariableValue(var.getName()) != null ||
				env.isPresent(var);
	}

}
