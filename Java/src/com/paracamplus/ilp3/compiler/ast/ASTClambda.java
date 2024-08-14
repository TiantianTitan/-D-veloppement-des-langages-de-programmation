/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.ast;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp3.ast.ASTlambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;

public class ASTClambda extends ASTlambda 
implements IASTClambda, Inamed {
    
    public ASTClambda (String closureName,
                       IASTvariable[] variables, 
                       IASTexpression body) {
        super(variables, body);
        this.closedVariables = new HashSet<>();
        this.closureName = closureName;
    }
    private final Set<IASTvariable> closedVariables;
    private final String closureName;

    @Override
	public Set<IASTvariable> getClosedVariables() {
        return closedVariables;
    }

    @Override
	public void setClosedVariables(Set<IASTClocalVariable> closedVariables) {
        this.closedVariables.addAll(closedVariables);
    }

    @Override
	public String getName() {
        return closureName;
    }
    
    @Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return ((IASTCvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
	}
}
