/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import com.paracamplus.ilp1.compiler.interfaces.IDestination;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class AssignDestination implements IDestination {

    // FIXME
    
    public AssignDestination (IASTvariable variable) {
        this.variable = variable;
    }
    private final IASTvariable variable;
    
    @Override
	public String compile() {
        return variable.getMangledName() + " = ";
    }

}
