/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ast;

import java.util.Arrays;
import java.util.List;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;

public class ASTprogram extends com.paracamplus.ilp1.ast.ASTprogram 
	implements IASTprogram {
	
    public ASTprogram(IASTfunctionDefinition[] functions,
                      IASTexpression expression) {
    	super(expression);
        this.functions = Arrays.asList(functions);
    }
    protected List<IASTfunctionDefinition> functions;
    
    @Override
	public IASTfunctionDefinition[] getFunctionDefinitions() {
        return functions.toArray(new IASTfunctionDefinition[0]);
    }
}