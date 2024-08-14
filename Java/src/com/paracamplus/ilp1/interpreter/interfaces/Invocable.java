/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.Interpreter;

public interface Invocable {
    int getArity();
    Object apply(Interpreter interpreter, Object[] argument) 
            throws EvaluationException;
}
