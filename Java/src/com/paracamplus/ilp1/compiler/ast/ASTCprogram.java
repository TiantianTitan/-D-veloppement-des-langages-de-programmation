/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.ast;

import java.util.HashSet;
import java.util.Set;


import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTCprogram extends com.paracamplus.ilp1.ast.ASTprogram 
implements IASTCprogram {

    public ASTCprogram (IASTexpression expression) {
        super(expression);
        this.globalVariables = new HashSet<>();
    }
    protected Set<IASTCglobalVariable> globalVariables;
    
    @Override
	public Set<IASTCglobalVariable> getGlobalVariables() {
        return globalVariables;
    }

    @Override
	public void setGlobalVariables(Set<IASTCglobalVariable> gvs) {
        globalVariables = gvs;        
    }

}
