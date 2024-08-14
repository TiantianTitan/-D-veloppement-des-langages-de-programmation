package com.paracamplus.ilp4.ilp4tme10.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.ilp4tme10.compiler.interfaces.IASTCvisitorTME10;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;

import java.util.Set;

public class FreeVariableCollectorTME10
extends com.paracamplus.ilp4.compiler.FreeVariableCollector 
implements IASTCvisitorTME10<Void, Set<IASTClocalVariable>, CompilationException> {

	public FreeVariableCollectorTME10(IASTCprogram program) {

		super(program);

	}


	@Override
	public Void visit(IASTexists iast, Set<IASTClocalVariable> data) throws CompilationException {

		return null;
	}


	@Override
	public Void visit(IASTdefined iast, Set<IASTClocalVariable> data) throws CompilationException {

		return iast.getVariable().accept(this,  data);
	}

}
