/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvariable;

public abstract interface IASTCvariable 
extends IASTvariable, IASTCvisitable {
    boolean isMutable();
    void setMutable();
}
