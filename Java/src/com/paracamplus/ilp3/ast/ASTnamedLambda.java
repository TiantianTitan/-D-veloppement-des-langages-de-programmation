/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.ast;

import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTnamedLambda extends ASTlambda implements IASTnamedLambda {

    public ASTnamedLambda (IASTvariable functionVariable,
            IASTvariable[] variables, 
            IASTexpression body) {
        super(variables, body);
        this.functionVariable = functionVariable;
    }            
    private final IASTvariable functionVariable;
    
    @Override
	public IASTvariable getFunctionVariable() {
        return functionVariable;
    }
}
