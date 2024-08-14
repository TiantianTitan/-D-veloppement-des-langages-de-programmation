/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

@SuppressWarnings("serial")
public class CompilationException extends Exception {

    public CompilationException(String msg) {
        super(msg);
    }

    public CompilationException(Exception e) {
        super(e);
    }
}
