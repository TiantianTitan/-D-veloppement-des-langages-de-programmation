/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp3.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTvisitable;

public class ASTlambda extends ASTexpression 
implements IASTlambda, IASTvisitable {
    
    public ASTlambda (IASTvariable[] variables, IASTexpression body) {
        this.variables = variables;
        this.body = body;
    }
    private final IASTvariable[] variables;
    private final IASTexpression body;

    @Override
	public IASTvariable[] getVariables() {
        return variables;
    }

    @Override
	public IASTexpression getBody() {
        return body;
    }

 	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
	}
}
