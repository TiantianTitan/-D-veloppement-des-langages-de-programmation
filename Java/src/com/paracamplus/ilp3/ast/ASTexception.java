/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.ast;

@SuppressWarnings("serial")
public class ASTexception extends Exception {
    public ASTexception (final Throwable cause) {
        super(cause);
    }

    public ASTexception (final String message) {
        super(message);
    }
}
