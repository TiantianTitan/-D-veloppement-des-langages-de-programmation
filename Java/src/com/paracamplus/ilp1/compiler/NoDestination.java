/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import com.paracamplus.ilp1.compiler.interfaces.IDestination;

public class NoDestination implements IDestination {

    public static final NoDestination NO_DESTINATION = 
            new NoDestination();
    
    private NoDestination () {}
    
    @Override
	public String compile() {
        return "";
    }
}
