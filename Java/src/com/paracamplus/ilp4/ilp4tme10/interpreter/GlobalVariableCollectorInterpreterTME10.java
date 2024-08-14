package com.paracamplus.ilp4.ilp4tme10.interpreter;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
//import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interpreter.EmptyLexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitorTME10;


public class GlobalVariableCollectorInterpreterTME10 implements
        IASTvisitorTME10<Void, ILexicalEnvironment, EvaluationException> {

	protected Set<String> globals;
	
	public GlobalVariableCollectorInterpreterTME10() {
		globals= new HashSet<>();		
	}
	
	public Set<String> getGlobals() {
		return globals;
	}
	

	public GlobalVariableCollectorInterpreterTME10 visit(IASTprogram iast)
			throws EvaluationException {
		ILexicalEnvironment env = new EmptyLexicalEnvironment();

		for (IASTclassDefinition c : iast.getClassDefinitions()) {
			ILexicalEnvironment methodenv = env;
			for (IASTmethodDefinition m : c.getProperMethodDefinitions()) {
				for (IASTvariable v : m.getVariables()) {
					methodenv.extend(v, null);
				}
				m.getBody().accept(this,  methodenv);
			}
		}

		for (IASTfunctionDefinition f : iast.getFunctionDefinitions()) {
			globals.add(f.getFunctionVariable().getName());
		}
		for (IASTfunctionDefinition f : iast.getFunctionDefinitions()) {
			ILexicalEnvironment funenv = env;
			for (IASTvariable v : f.getVariables()) {
				funenv.extend(v, null);
			}
			f.getBody().accept(this, funenv);
		}

		iast.getBody().accept(this,  env);
		return this;
	}
	    


	@Override
	public Void visit(IASTalternative iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getCondition().accept(this, locals);
		iast.getConsequence().accept(this, locals);
		if (iast.getAlternant()!=null) {
			iast.getAlternant().accept(this, locals);
		}
        return null;
	}

	@Override
	public Void visit(IASTassignment iast, ILexicalEnvironment locals)
			throws EvaluationException {
		IASTvariable var = iast.getVariable();
		if (!locals.isPresent(var)) {
			globals.add(var.getName());
		}
		iast.getExpression().accept(this,  locals);
		return null;
	}

	@Override
	public Void visit(IASTbinaryOperation iast,
			ILexicalEnvironment locals) throws EvaluationException {
		iast.getLeftOperand().accept(this, locals);
		iast.getRightOperand().accept(this, locals);
		return null;
	}

	@Override
	public Void visit(IASTblock iast, ILexicalEnvironment locals)
			throws EvaluationException {

		ILexicalEnvironment newenv = locals;
		for (IASTbinding b : iast.getBindings()) {

			newenv = newenv.extend(b.getVariable(), null);
		}

		iast.getBody().accept(this, newenv);
		return null;
	}

	@Override
	public Void visit(IASTboolean iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTcodefinitions iast,
			ILexicalEnvironment locals) throws EvaluationException {

		ILexicalEnvironment newenv = locals;
		for (IASTnamedLambda l : iast.getFunctions()) {
			newenv = newenv.extend(l.getFunctionVariable(), null);
		}
		for (IASTnamedLambda l : iast.getFunctions()) {
			ILexicalEnvironment funenv = newenv;

			for (IASTvariable v : l.getVariables()) {
				funenv = funenv.extend(v, null);
			}

			l.getBody().accept(this,  funenv);
		}

		iast.getBody().accept(this, newenv);
		return null;
	}

	@Override
	public Void visit(IASTfloat iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTinstantiation iast,
			ILexicalEnvironment locals) throws EvaluationException {
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this,  locals);
		}
		return null;
	}

	@Override
	public Void visit(IASTinteger iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTinvocation iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getFunction().accept(this, locals);
		for (IASTexpression i : iast.getArguments()) {
			i.accept(this,  locals);
		}
		return null;
	}

	@Override
	public Void visit(IASTlambda iast, ILexicalEnvironment locals)
			throws EvaluationException {
		ILexicalEnvironment newenv = locals;

		for (IASTvariable v : iast.getVariables()) {
			newenv = newenv.extend(v, null);
		}

		iast.getBody().accept(this, newenv);
		return null;
	}

	@Override
	public Void visit(IASTloop iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getCondition().accept(this, locals);
		iast.getBody().accept(this, locals);
		return null;
	}

	@Override
	public Void visit(IASTfieldRead iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getTarget().accept(this, locals);
		return null;
	}

	@Override
	public Void visit(IASTself iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTsend iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getReceiver().accept(this,  locals);
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this, locals);
		}
		return null;
	}

	@Override
	public Void visit(IASTsequence iast, ILexicalEnvironment locals)
			throws EvaluationException {
		for (IASTexpression e : iast.getExpressions()) {
			e.accept(this, locals);
		}
		return null;
	}

	@Override
	public Void visit(IASTstring iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTsuper iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTtry iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getBody().accept(this, locals);
		if (iast.getCatcher()!=null)  {
			iast.getCatcher().accept(this, locals);
		}
		if (iast.getFinallyer()!=null) {
			iast.getFinallyer().accept(this,locals);
		}
		return null;
	}

	@Override
	public Void visit(IASTunaryOperation iast,
			ILexicalEnvironment locals) throws EvaluationException {
		iast.getOperand().accept(this,  locals);
		return null;
	}

	@Override
	public Void visit(IASTvariable iast, ILexicalEnvironment locals)
			throws EvaluationException {

		if (!locals.isPresent(iast)) {
			globals.add(iast.getName());
		}
		return null;
	}

	@Override
	public Void visit(IASTfieldWrite iast, ILexicalEnvironment locals)
			throws EvaluationException {
		iast.getTarget().accept(this,  locals);
		iast.getValue().accept(this, locals);
		return null;
	}

	@Override
	public Void visit(IASTexists iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

	@Override
	public Void visit(IASTdefined iast, ILexicalEnvironment locals)
			throws EvaluationException {
		return null;
	}

}
