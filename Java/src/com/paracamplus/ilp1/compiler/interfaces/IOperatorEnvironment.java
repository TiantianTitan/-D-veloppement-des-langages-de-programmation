/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASToperator;

public interface IOperatorEnvironment {
    String getUnaryOperator (IASToperator operator) 
            throws CompilationException;
    String getBinaryOperator (IASToperator operator) 
            throws CompilationException;
    void addUnaryOperator (String operator, String cOperator)
            throws CompilationException;
    void addBinaryOperator (String operator, String cOperator) 
            throws CompilationException;

}
