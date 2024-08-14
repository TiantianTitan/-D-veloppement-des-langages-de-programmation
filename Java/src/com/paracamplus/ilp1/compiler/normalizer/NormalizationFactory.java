/* *****************************************************************
 * ilp1 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp1
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import java.util.concurrent.atomic.AtomicInteger;

import com.paracamplus.ilp1.ast.ASTalternative;
import com.paracamplus.ilp1.ast.ASTbinaryOperation;
import com.paracamplus.ilp1.ast.ASTboolean;
import com.paracamplus.ilp1.ast.ASTfloat;
import com.paracamplus.ilp1.ast.ASTinteger;
import com.paracamplus.ilp1.ast.ASToperator;
import com.paracamplus.ilp1.ast.ASTsequence;
import com.paracamplus.ilp1.ast.ASTstring;
import com.paracamplus.ilp1.ast.ASTunaryOperation;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.ast.ASTCblock;
import com.paracamplus.ilp1.compiler.ast.ASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.ast.ASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.ast.ASTCglobalVariable;
import com.paracamplus.ilp1.compiler.ast.ASTClocalVariable;
import com.paracamplus.ilp1.compiler.ast.ASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class NormalizationFactory
implements INormalizationFactory {
    
    public NormalizationFactory() {
        this.count = new AtomicInteger(0);
    }
    protected AtomicInteger count;
    
    @Override
	public IASTCprogram newProgram(IASTexpression expression) {
        return new ASTCprogram(expression); 
    }

    // Various types of variables:
    
    @Override
	public IASTCvariable newVariable(String name) throws CompilationException {
        throw new CompilationException("Uncategorized variable " + name);
    }
   
    @Override
	public IASTClocalVariable newLocalVariable(String name) {
        String newName = name + count.incrementAndGet();
        return new ASTClocalVariable(newName);
    }
    @Override
	public IASTCglobalVariable newGlobalVariable(String name) {
        return new ASTCglobalVariable(name);
    }
   
   
    
    @Override
	public IASToperator newOperator(String name) {
        return new ASToperator(name);
    }
        
    
    @Override
	public IASTexpression newSequence(IASTexpression[] asts) {
        return new ASTsequence(asts);
    }
    
    @Override
	public IASTexpression newAlternative(
            IASTexpression condition,
            IASTexpression consequence, 
            IASTexpression alternant) {
        return new ASTalternative(condition, consequence, alternant);
    }
    
    // various types of invocation
    
    @Override
	public IASTexpression newInvocation(
            IASTexpression function,
            IASTexpression[] arguments) throws CompilationException {
        throw new CompilationException("Uncategorized invocation ");
    }
    
    @Override
	public IASTexpression newUnaryOperation(
            IASToperator operator,
            IASTexpression operand) {
        return new ASTunaryOperation(operator, operand);
    }
    
    @Override
	public IASTexpression newBinaryOperation(
            IASToperator operator,
            IASTexpression leftOperand, 
            IASTexpression rightOperand) {
        return new ASTbinaryOperation(operator, leftOperand, rightOperand);
    }
    
    @Override
	public IASTexpression newIntegerConstant(String value) {
        return new ASTinteger(value);
    }
    
    @Override
	public IASTexpression newFloatConstant(String value) {
        return new ASTfloat(value);
    }
    
    @Override
	public IASTexpression newStringConstant(String value) {
        return new ASTstring(value);
    }
    
    @Override
	public IASTexpression newBooleanConstant(String value) {
        return new ASTboolean(value);
    }
    
    
    @Override
	public IASTCblock newBlock(IASTCblock.IASTCbinding[] binding, IASTexpression body) {
        return new ASTCblock(binding, body);
    }
    @Override
	public IASTCblock.IASTCbinding newBinding(IASTvariable variable, IASTexpression initialisation) {
        return new ASTCblock.ASTCbinding(variable, initialisation);
    }
    
    @Override
	public IASTCcomputedInvocation newComputedInvocation(
            IASTexpression function,
            IASTexpression[] arguments) {
        return new ASTCcomputedInvocation(function, arguments);
    }
  
    @Override
	public IASTCglobalInvocation newGlobalInvocation(
            IASTCglobalVariable function,
            IASTexpression[] arguments) {
        return new ASTCglobalInvocation(function, arguments);
    }

    
}
