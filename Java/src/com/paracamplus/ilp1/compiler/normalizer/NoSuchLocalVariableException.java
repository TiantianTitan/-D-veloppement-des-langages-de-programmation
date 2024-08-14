/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import com.paracamplus.ilp1.compiler.CompilationException;

@SuppressWarnings("serial")
public class NoSuchLocalVariableException extends CompilationException {

    public NoSuchLocalVariableException(String msg) {
        super(msg);
    }

    public NoSuchLocalVariableException(Exception e) {
        super(e);
    }
}
