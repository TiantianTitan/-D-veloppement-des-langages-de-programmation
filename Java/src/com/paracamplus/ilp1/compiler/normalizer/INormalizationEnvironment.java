/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface INormalizationEnvironment {
    INormalizationEnvironment extend(IASTvariable oldv, IASTvariable newv);
    IASTvariable renaming(IASTvariable variable) 
            throws NoSuchLocalVariableException;
}
