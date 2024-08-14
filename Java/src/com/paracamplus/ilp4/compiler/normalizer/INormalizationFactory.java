/* *****************************************************************
 * ilp4 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp4
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.normalizer;

import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface INormalizationFactory 
extends com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory {

    IASTCprogram newProgram(IASTCfunctionDefinition[] functions,
                            IASTCclassDefinition[] clazzes, 
                            IASTexpression expression);

    
    // Class related
    
    IASTCglobalFunctionVariable newMethodVariable(String name);
    
     IASTCclassDefinition newClassDefinition(
            String className,
            IASTCclassDefinition superClass, 
            String[] fieldNames,
            IASTCmethodDefinition[] methodDefinitions);

     IASTCmethodDefinition newMethodDefinition(
             IASTvariable methodVariable,
             IASTvariable[] variables, 
             IASTexpression body,
             String methodName,
             IASTCclassDefinition definingClass );
     
     IASTexpression newInstantiation(IASTCclassDefinition clazz,
                                     IASTexpression[] arguments);

     IASTexpression newReadField(IASTCclassDefinition clazz,
                                 String fieldName, 
                                 IASTexpression target);

     IASTexpression newWriteField(IASTCclassDefinition clazz,
                                  String fieldName,
                                  IASTexpression target, 
                                  IASTexpression value);

     IASTvariable newSelf();

     IASTexpression newSend(String message, 
                            IASTexpression receiver,
                            IASTexpression[] arguments);
    
     IASTexpression newSuper();
}
