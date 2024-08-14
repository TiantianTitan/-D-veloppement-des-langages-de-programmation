/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.interfaces;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;

public interface IASTCmethodDefinition 
extends IASTmethodDefinition,
        IASTCfunctionDefinition,
        IASTCclassRelated {
    IASTCmethodDefinition findSuperMethod() throws CompilationException;
}
