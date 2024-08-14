package com.paracamplus.ilp4.ilp4tme10.ast;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTfactoryTME10;


public class ASTfactoryTME10
	extends com.paracamplus.ilp4.ast.ASTfactory 
	implements IASTfactoryTME10 {

	public IASTdefined newDefined(IASTvariable variable) {
		return new ASTdefined(variable);
	}

	public IASTexists newExists(IASTvariable variable) {
		return new ASTexists(variable);
	}

}
