/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.annotation.OrNull;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTalternative extends ASTexpression
implements IASTalternative {
    
	public ASTalternative(IASTexpression condition,
                          IASTexpression consequence,
                          IASTexpression alternant ) {
		this.condition = condition;
		this.consequence = consequence;
		this.alternant = alternant;
	}
	private final IASTexpression condition;
	private final IASTexpression consequence;
	private @OrNull final IASTexpression alternant;

	@Override
	public IASTexpression getCondition() {
		return condition;
	}

	@Override
	public IASTexpression getConsequence() {
		return consequence;
	}

	@Override @OrNull
	public IASTexpression getAlternant() {
		return alternant;
	}

	@Override
	public boolean isTernary () {
	    return this.alternant != null;
	}

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
}
