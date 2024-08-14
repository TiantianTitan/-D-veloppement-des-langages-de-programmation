/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.ast;


import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTCprogram 
extends com.paracamplus.ilp3.compiler.ast.ASTCprogram 
implements IASTCprogram {

public ASTCprogram (IASTCfunctionDefinition[] functions,
                        IASTCclassDefinition[] clazzes, 
                        IASTexpression expression) {
    	super(functions, expression);
        this.clazzes = clazzes;
}
    
protected IASTclassDefinition[] clazzes;
    

@Override
public IASTCclassDefinition[] getClassDefinitions() {
	IASTclassDefinition[] cds = clazzes;
	IASTCclassDefinition[] newcds = new IASTCclassDefinition[cds.length];
	for ( int i=0 ; i<cds.length ; i++ ) {
		newcds[i] = (IASTCclassDefinition) cds[i];
	}
	return newcds;
}

  
}
