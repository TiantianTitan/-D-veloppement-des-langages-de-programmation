/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.ast;

import java.util.concurrent.atomic.AtomicInteger;

import com.paracamplus.ilp3.compiler.interfaces.IASTCnamedLambda;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTCnamedLambda extends ASTClambda 
implements IASTCnamedLambda {

    public ASTCnamedLambda (IASTvariable functionVariable,
                            IASTvariable[] variables, 
                            IASTexpression body) {
        super(functionVariable.getName() + "_" + counter.incrementAndGet(), 
              variables, body);
        this.functionVariable = functionVariable;
    }
    private final IASTvariable functionVariable;
    private static final AtomicInteger counter = new AtomicInteger(0);
    
    @Override
	public IASTvariable getFunctionVariable() {
        return functionVariable;
    }
}
