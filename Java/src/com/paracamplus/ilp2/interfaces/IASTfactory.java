/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.interfaces;


import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface IASTfactory extends com.paracamplus.ilp1.interfaces.IASTfactory {
    IASTprogram newProgram(
    		IASTfunctionDefinition[] functions,
            IASTexpression expression);
    
    IASTexpression newLoop(IASTexpression condition,
                           IASTexpression body);

    IASTfunctionDefinition newFunctionDefinition(
            IASTvariable functionVariable,
            IASTvariable[] variables,
            IASTexpression body);
    
    IASTexpression newAssignment(IASTvariable variable,
            IASTexpression value);
    
}
