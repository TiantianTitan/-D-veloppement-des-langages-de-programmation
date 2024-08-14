/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTvisitable;

public class ASTloop extends ASTexpression 
implements IASTloop, IASTvisitable {
    
    public ASTloop (IASTexpression condition, IASTexpression body) {
        this.condition = condition;
        this.body = body;
    }
    private final IASTexpression condition;
    private final IASTexpression body;

    @Override
	public IASTexpression getCondition() {
        return condition;
    }

    @Override
	public IASTexpression getBody() {
        return body;
    }

   @Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return ((IASTvisitor <Result, Data, Anomaly>) visitor).visit(this, data);
	}
}
