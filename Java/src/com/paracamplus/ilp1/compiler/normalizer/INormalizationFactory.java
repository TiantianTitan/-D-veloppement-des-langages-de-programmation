/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTvariable;

 public interface INormalizationFactory {

    IASTCprogram newProgram(IASTexpression expression);

    // Non uniform value types:
    
     IASTCvariable newVariable(String name) throws CompilationException;
     IASTCglobalVariable newGlobalVariable(String name);
     IASTClocalVariable newLocalVariable(String name);     
     
     IASToperator newOperator(String name);
    
    // Expression related:

     IASTexpression newSequence(IASTexpression[] asts);

     IASTexpression newAlternative(IASTexpression condition,
                                   IASTexpression consequence, 
                                   IASTexpression alternant);

     IASTexpression newInvocation(
            IASTexpression function,
            IASTexpression[] arguments) throws CompilationException;

     IASTexpression newUnaryOperation(IASToperator operator,
                                      IASTexpression operand);

     IASTexpression newBinaryOperation(IASToperator operator,
                                       IASTexpression leftOperand, 
                                       IASTexpression rightOperand);

     IASTexpression newIntegerConstant(String value);

     IASTexpression newFloatConstant(String value);

     IASTexpression newStringConstant(String value);

     IASTexpression newBooleanConstant(String value);


     IASTCblock newBlock(IASTCblock.IASTCbinding[] binding,
                         IASTexpression body);

     IASTCblock.IASTCbinding newBinding(IASTvariable variable, 
                                        IASTexpression initialisation);
     
     IASTexpression newComputedInvocation(
             IASTexpression function,
             IASTexpression[] arguments);
     
     
     IASTCglobalInvocation newGlobalInvocation(
             IASTCglobalVariable function,
             IASTexpression[] arguments);

    
}
