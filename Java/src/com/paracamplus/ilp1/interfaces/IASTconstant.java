/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interfaces;

public abstract interface IASTconstant extends IASTexpression {
	/** The accurate description of the constant */
    String getDescription();
    /** The constant as an accurate Java value */
    Object getValue ();
}
