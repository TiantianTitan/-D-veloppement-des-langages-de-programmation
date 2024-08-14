/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTfactory;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTfactory 
extends  com.paracamplus.ilp3.ast.ASTfactory
implements IASTfactory {

    @Override
	public IASTprogram newProgram(IASTfunctionDefinition[] functions,
                                  IASTclassDefinition[] clazzes, 
                                  IASTexpression expression) {
        return new ASTprogram(functions, clazzes, expression);
    }
    

    
    @Override
	public IASTclassDefinition newClassDefinition(
            String className,
            String superClassName, 
            String[] fieldNames,
            IASTmethodDefinition[] methodDefinitions) {
        return new ASTclassDefinition(
                className,
                superClassName,
                fieldNames,
                methodDefinitions );
    }

    @Override
	public IASTmethodDefinition newMethodDefinition(
            IASTvariable methodVariable,
            IASTvariable[] variables, 
            IASTexpression body,
            String methodName,
            String definingClassName ) {
        return new ASTmethodDefinition(
                methodVariable, variables, body, 
                methodName, definingClassName);
    }

    @Override
	public IASTinstantiation newInstantiation(
            String className,
            IASTexpression[] arguments) {
        return new ASTinstantiation(className, arguments);
    }

    @Override
	public IASTfieldRead newReadField(String fieldName, IASTexpression target) {
        return new ASTfieldRead(fieldName, target);
    }

    @Override
	public IASTfieldWrite newWriteField(
            String fieldName,
            IASTexpression target, 
            IASTexpression value) {
        return new ASTfieldWrite(fieldName, target, value);
    }

    @Override
	public IASTsend newSend(
            String message, 
            IASTexpression receiver,
            IASTexpression[] arguments) {
        return new ASTsend(message, receiver, arguments);
    }

    @Override
	public IASTself newSelf() {
        return new ASTself();
    }

    @Override
	public IASTsuper newSuper() {
        return new ASTsuper();
    }
}
