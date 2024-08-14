package com.paracamplus.ilp4.ilp4tme10.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.ilp4tme10.compiler.interfaces.IASTCvisitorTME10;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitorTME10;

public class ASTdefined extends ASTexpression implements IASTdefined {

	public ASTdefined(IASTvariable variable) {
		this.variable = variable;
	}
	
	protected IASTvariable variable;
	
	public IASTvariable getVariable() {
		return variable;
	}
	
	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data)
			throws Anomaly {
		if (visitor instanceof IASTCvisitorTME10) {
			return ((IASTCvisitorTME10<Result,Data,Anomaly>)visitor).visit(this,data);
		}
		else if (visitor instanceof IASTvisitorTME10) {
			return ((IASTvisitorTME10<Result,Data,Anomaly>)visitor).visit(this,data);
		}
		else {
		   	throw new IllegalArgumentException(
	    			"pas IASTvisitor / IASTCvisitor");
		}
	}

}
