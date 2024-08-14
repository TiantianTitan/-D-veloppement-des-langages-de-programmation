/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.ast;

import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor;

public class ASTClocalVariable extends ASTCvariable 
implements IASTClocalVariable {

    public ASTClocalVariable (String name) {
        super(name);
        this.closed = false;
    }
    private boolean closed;

    @Override
	public boolean isClosed() {
        return closed;
    }

    @Override
	public void setClosed() {
        closed = true;        
    }
    
    @Override
	public <Result, Data, Anomaly extends Throwable> Result 
    accept(IASTCvisitor<Result, Data, Anomaly> visitor, Data data)
        throws Anomaly {
    return visitor.visit(this, data);
}
}
