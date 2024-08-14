/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.ast;


import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp3.interfaces.IASTprogram;

public class ASTprogram extends com.paracamplus.ilp2.ast.ASTprogram 
implements IASTprogram {

	public ASTprogram(IASTfunctionDefinition[] functions,
			IASTexpression expression) {
		super(functions, expression);
	}
}
