/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.normalizer;


import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCnamedLambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp1.interfaces.IASTvariable;

	
 public interface INormalizationFactory 
 extends com.paracamplus.ilp2.compiler.normalizer.INormalizationFactory {

    @Override
	IASTCprogram newProgram(IASTCfunctionDefinition[] functions,
                            IASTexpression expression);

    IASTClocalFunctionVariable newLocalFunctionVariable(String name);
    
    
    IASTexpression newLocalFunctionInvocation(
            IASTClocalFunctionVariable function,
            IASTexpression[] arguments);

 
    IASTexpression newTry (IASTexpression body,
                            IASTlambda catcher,
                            IASTexpression finallyer );
    
    IASTClambda newLambda (IASTvariable[] variables,
                            IASTexpression body );

    IASTCnamedLambda newNamedLambda(IASTvariable newFunctionVar,
                                    IASTvariable[] newvariables,
                                    IASTexpression newbody);
    
     IASTCcodefinitions newCodefinitions (
            IASTCnamedLambda[] functions,
            IASTexpression body );
    

}
