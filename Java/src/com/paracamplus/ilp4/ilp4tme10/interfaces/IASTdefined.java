package com.paracamplus.ilp4.ilp4tme10.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface IASTdefined extends IASTexpression {
	public IASTvariable getVariable();
}
