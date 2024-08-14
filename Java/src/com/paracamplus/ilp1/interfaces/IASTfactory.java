/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interfaces;

import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;


public interface IASTfactory {
    IASTprogram newProgram(
            IASTexpression expression);
    
    IASTexpression newSequence(IASTexpression[] asts);

    IASTexpression newAlternative(
            IASTexpression condition,
            IASTexpression consequence,
            IASTexpression alternant);

    IASToperator newOperator(String name);
    
    IASTvariable newVariable(String name);
    

    IASTexpression newUnaryOperation(
            IASToperator operator,
            IASTexpression operand);

    IASTexpression newBinaryOperation(
            IASToperator operator,
            IASTexpression leftOperand,
            IASTexpression rightOperand);

    IASTexpression newIntegerConstant(String value);

    IASTexpression newFloatConstant(String value);

    IASTexpression newStringConstant(String value);

    IASTexpression newBooleanConstant(String value);


    IASTexpression newBlock(IASTbinding[] binding,
                            IASTexpression body);

    IASTbinding newBinding(IASTvariable v, IASTexpression exp);
    
    IASTexpression newInvocation(
            IASTexpression function,
            IASTexpression[] arguments);
   
}
