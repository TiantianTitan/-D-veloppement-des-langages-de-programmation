/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;



public class ASTCprogram extends com.paracamplus.ilp1.compiler.ast.ASTCprogram 
implements com.paracamplus.ilp2.compiler.interfaces.IASTCprogram {

    public ASTCprogram (IASTCfunctionDefinition[] functions,
                        IASTexpression expression) {
        super(expression);
        this.functions = functions;
    }
    
    protected IASTCfunctionDefinition[] functions;
   
    @Override
	public IASTCfunctionDefinition[] getFunctionDefinitions() {
    	return functions;
    }
}
