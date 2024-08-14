/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTinvocation extends ASTexpression implements IASTinvocation {
    
    public ASTinvocation (IASTexpression function, IASTexpression[] arguments) {
        this.function = function;
        this.arguments = arguments;
    }
    private final IASTexpression function;
    private final IASTexpression[] arguments;
    
    @Override
	public IASTexpression getFunction () {
        return function;
    }
    @Override
	public IASTexpression[] getArguments () {
        return arguments;
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
}
