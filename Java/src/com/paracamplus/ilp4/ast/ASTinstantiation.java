/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTinstantiation extends ASTexpression
implements IASTinstantiation {
    
    public  ASTinstantiation (String className, IASTexpression[] arguments) {
        this.className = className;
        this.arguments = arguments;
    }
    private final String className;
    private final IASTexpression[] arguments;

    @Override
	public String getClassName() {
        return className;
    }

    @Override
	public IASTexpression[] getArguments() {
        return arguments;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
        return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }
}
