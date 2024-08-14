/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface IASTfactory extends com.paracamplus.ilp2.interfaces.IASTfactory {
    @Override
	IASTprogram newProgram(
    		IASTfunctionDefinition[] functions,
            IASTexpression expression);
    
    IASTexpression newTry (IASTexpression body,
                           IASTlambda catcher,
                           IASTexpression finallyer );

    IASTlambda newLambda (IASTvariable[] variables,
                              IASTexpression body );

    IASTnamedLambda newNamedLambda(
            IASTvariable functionVariable,
            IASTvariable[] variables,
            IASTexpression body );
    
    IASTexpression newCodefinitions(IASTnamedLambda[] functions,
                                    IASTexpression body);


}
