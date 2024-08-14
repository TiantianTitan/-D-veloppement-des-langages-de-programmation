/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.ast.ASTfunctionDefinition;

public class ASTmethodDefinition extends ASTfunctionDefinition
implements IASTmethodDefinition {
    
    public ASTmethodDefinition(IASTvariable methodVariable, 
                               IASTvariable[] variables,
                               IASTexpression body,
                               String methodName,
                               String definingClassName ) {
        super(methodVariable, variables, body);
        this.definingClassName = definingClassName;
        this.methodName = methodName;
    }
    private final String definingClassName;
    private final String methodName;

    @Override
	public String getDefiningClassName() {
        return definingClassName;
    }
    
    @Override
	public String getMethodName() {
        return methodName;
    }
}
