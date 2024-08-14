/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.ast;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp1.annotation.OrNull;
import com.paracamplus.ilp4.ast.ASTmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTCmethodDefinition extends ASTmethodDefinition 
implements IASTCmethodDefinition {
    public ASTCmethodDefinition (
            IASTvariable methodVariable,
            IASTvariable[] variables, 
            IASTexpression body,
            String methodName,
            IASTCclassDefinition definingClass ) {
        super(methodVariable, variables, body, 
                methodName, definingClass.getName());
        this.definingClass = definingClass;
        this.closedVariables = new HashSet<>();
    }
    private final IASTCclassDefinition definingClass;
    private final Set<IASTvariable> closedVariables;
    
    @Override
	public IASTCclassDefinition getDefiningClass () {
        return definingClass;
    }

    @Override
	public String getCName() {
        return "ilp__" + getFunctionVariable().getMangledName();
    }

    @Override
	public Set<IASTvariable> getClosedVariables() {
        return closedVariables;
    }

    @Override
	public void setClosedVariables(Set<IASTClocalVariable> newvars) {
        this.closedVariables.addAll(closedVariables);
    }

    @Override
	public @OrNull IASTCmethodDefinition findSuperMethod() {
        IASTCclassDefinition superClass = getDefiningClass().getSuperClass();
        IASTCmethodDefinition[] methods = superClass.getTotalMethodDefinitions();
        for ( IASTCmethodDefinition method : methods ) {
            if ( getMethodName().equals(method.getMethodName()) ) {
                return method;
            }
        }
        return null;
    }
}
