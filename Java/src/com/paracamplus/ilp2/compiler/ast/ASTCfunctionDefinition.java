/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.ast;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp2.ast.ASTfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTCfunctionDefinition extends ASTfunctionDefinition 
implements IASTCfunctionDefinition {

    public ASTCfunctionDefinition (IASTvariable functionVariable,
                                   IASTvariable[] variables,
                                   IASTexpression body ) {
        super(functionVariable, variables, body);
        this.closedVariables = new HashSet<>();
    }
    private final Set<IASTvariable> closedVariables;

    @Override
	public Set<IASTvariable> getClosedVariables() {
        return closedVariables;
    }

    @Override
	public void setClosedVariables(Set<IASTClocalVariable> closedVariables) {
        this.closedVariables.addAll(closedVariables);
    }

    @Override
	public String getCName() {
        return "ilp__" + getFunctionVariable().getMangledName();
    }
}
