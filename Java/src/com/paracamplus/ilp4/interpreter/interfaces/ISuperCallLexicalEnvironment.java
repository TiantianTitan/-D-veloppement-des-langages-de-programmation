/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public interface ISuperCallLexicalEnvironment 
extends com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment {
    ISuperCallLexicalEnvironment extend(ISuperCallInformation isci);
    ISuperCallInformation getSuperCallInformation() 
            throws EvaluationException;
}
