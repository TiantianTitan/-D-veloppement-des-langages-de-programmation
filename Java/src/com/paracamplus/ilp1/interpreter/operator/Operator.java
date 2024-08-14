/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import com.paracamplus.ilp1.interpreter.interfaces.IOperator;

public abstract class Operator implements IOperator {

    public Operator (String name) {
        this.name = name;
    }
    private final String name;
    
    @Override
	public String getName() {
        return this.name;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if (obj == null ) {
            return false;
        }
        if ( obj instanceof Operator ) {
            return ((Operator) obj).getName().equals(this.name);
        }
        return false;
    }
}
