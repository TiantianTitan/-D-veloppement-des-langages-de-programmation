/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTvariable extends ASTnamed implements IASTvariable {

    public ASTvariable (String name) {
        super(name);
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(this.getClassShortName());
        sb.append(" name='");
        sb.append(this.getName());
        sb.append("'/>");
        return sb.toString();
    }
}
