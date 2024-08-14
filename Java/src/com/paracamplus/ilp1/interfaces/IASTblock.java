/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interfaces;

public interface IASTblock extends IASTexpression {
    
    interface IASTbinding extends IAST {
        IASTvariable getVariable();
        IASTexpression getInitialisation();
    }

	IASTbinding[] getBindings();
	IASTexpression getBody();
}
