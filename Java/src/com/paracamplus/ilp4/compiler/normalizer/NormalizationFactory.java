/* *****************************************************************
 * ilp4 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp4
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.normalizer;


import com.paracamplus.ilp4.ast.ASTself;
import com.paracamplus.ilp4.ast.ASTsend;
import com.paracamplus.ilp4.ast.ASTsuper;
import com.paracamplus.ilp4.compiler.ast.ASTCclassDefinition;
import com.paracamplus.ilp4.compiler.ast.ASTCfieldRead;
import com.paracamplus.ilp4.compiler.ast.ASTCfieldWrite;
import com.paracamplus.ilp4.compiler.ast.ASTCinstantiation;
import com.paracamplus.ilp4.compiler.ast.ASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.ast.ASTCprogram;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldWrite;
import com.paracamplus.ilp2.compiler.ast.ASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCinstantiation;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class NormalizationFactory
extends com.paracamplus.ilp3.compiler.normalizer.NormalizationFactory
implements INormalizationFactory {
    
  
    @Override
	public IASTCprogram newProgram(
            IASTCfunctionDefinition[] functions,
            IASTCclassDefinition[] clazzes, 
            IASTexpression expression) {
        return new ASTCprogram(functions, clazzes, expression); 
    }

 
    // Class related
    
    @Override
	public IASTCglobalFunctionVariable newMethodVariable(String methodName) {
        String newName = methodName + "_" + count.incrementAndGet();
        return new ASTCglobalFunctionVariable(newName);
    }

    @Override
	public IASTCclassDefinition newClassDefinition(
            String className,
            IASTCclassDefinition superClass, 
            String[] fieldNames,
            IASTCmethodDefinition[] methodDefinitions) {
        return new ASTCclassDefinition(
                className,
                superClass,
                fieldNames,
                methodDefinitions );
    }

    @Override
	public IASTCmethodDefinition newMethodDefinition(
            IASTvariable methodVariable,
            IASTvariable[] variables, 
            IASTexpression body,
            String methodName,
            IASTCclassDefinition definingClass ) {
        return new ASTCmethodDefinition(
                methodVariable, variables, body, methodName, definingClass);
    }

    @Override
	public IASTCinstantiation newInstantiation(
            IASTCclassDefinition clazz,
            IASTexpression[] arguments) {
        return new ASTCinstantiation(clazz, arguments);
    }

    @Override
	public IASTCfieldRead newReadField(
            IASTCclassDefinition clazz,
            String fieldName, 
            IASTexpression target) {
        return new ASTCfieldRead(clazz, fieldName, target);
    }

    @Override
	public IASTCfieldWrite newWriteField(
            IASTCclassDefinition clazz,
            String fieldName,
            IASTexpression target, 
            IASTexpression value) {
        return new ASTCfieldWrite(clazz, fieldName, target, value);
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
