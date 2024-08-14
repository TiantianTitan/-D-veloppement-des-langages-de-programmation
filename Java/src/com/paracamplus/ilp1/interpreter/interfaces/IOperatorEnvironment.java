/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;

import com.paracamplus.ilp1.interfaces.IASToperator;

public interface IOperatorEnvironment {
    IOperator getUnaryOperator (IASToperator operator) 
            throws EvaluationException;
    IOperator getBinaryOperator (IASToperator operator) 
            throws EvaluationException;
    void addOperator (IOperator operator) throws EvaluationException;
}
