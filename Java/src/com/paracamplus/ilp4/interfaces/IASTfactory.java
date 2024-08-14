/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface IASTfactory extends com.paracamplus.ilp3.interfaces.IASTfactory{
    IASTprogram newProgram(
    		IASTfunctionDefinition[] functions,
    		IASTclassDefinition[] clazzes,
            IASTexpression expression);
    

    IASTclassDefinition newClassDefinition(
            String className,
            String superClassName,
            String[] fieldNames,
            IASTmethodDefinition[] methodDefinitions );
    
    IASTmethodDefinition newMethodDefinition(
            IASTvariable methodVariable,
            IASTvariable[] variables,
            IASTexpression body, 
            String methodName,
            String definingClassName  );
    
    IASTexpression newInstantiation(
            String className,
            IASTexpression[] arguments );
    
    IASTexpression newReadField(
            String fieldName,
            IASTexpression object );
    
    IASTexpression newWriteField(
            String fieldName,
            IASTexpression object,
            IASTexpression value );
    
    IASTvariable newSelf();
    
    IASTexpression newSend(
            String message,
            IASTexpression receiver,
            IASTexpression[] arguments );
    
    IASTexpression newSuper();
}
