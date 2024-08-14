package com.paracamplus.ilp4.ilp4tme10.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;


public interface IASTfactoryTME10 extends com.paracamplus.ilp4.interfaces.IASTfactory {
	public IASTexpression newDefined(IASTvariable variable);
	public IASTexpression newExists(IASTvariable variable);
}
