/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.ast;

import com.paracamplus.ilp1.ast.ASTinvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTCglobalInvocation extends ASTinvocation
implements IASTCglobalInvocation {
    
    public ASTCglobalInvocation (IASTexpression function, 
                                IASTexpression[] arguments) {
        super(function, arguments);
    }
    
    @Override
	public IASTCglobalVariable getFunction() {
        return (IASTCglobalVariable) super.getFunction();
    }
}
