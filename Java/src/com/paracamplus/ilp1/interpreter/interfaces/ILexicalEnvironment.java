/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IEnvironment;

public interface ILexicalEnvironment 
extends IEnvironment<IASTvariable, Object, EvaluationException> {
    @Override
	ILexicalEnvironment extend(IASTvariable variable, Object value);
    @Override
	ILexicalEnvironment getNext() throws EvaluationException;
}
