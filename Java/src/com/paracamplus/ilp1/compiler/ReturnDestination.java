/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import com.paracamplus.ilp1.compiler.interfaces.IDestination;

public class ReturnDestination implements IDestination {
    
    private ReturnDestination () {}
    
    public static final ReturnDestination RETURN_DESTINATION =
            new  ReturnDestination();

    @Override
	public String compile() {
        return "return ";
    }
}
