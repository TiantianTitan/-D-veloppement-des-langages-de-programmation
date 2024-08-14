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
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp3.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTvisitable;

public class ASTtry extends ASTexpression 
implements IASTtry, IASTvisitable {
    
    public ASTtry (IASTexpression body,
                   IASTlambda catcher,
                   IASTexpression finallyer ) {
        this.body = body;
        this.catcher = catcher;
        this.finallyer = finallyer;
    }
    private final IASTexpression body;
    private final IASTlambda catcher;
    private final IASTexpression finallyer;

    @Override
	public IASTexpression getBody() {
        return body;
    }

    @Override
	public IASTlambda getCatcher() {
        return catcher;
    }

    @Override
	public IASTexpression getFinallyer() {
        return finallyer;
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
	}
}
