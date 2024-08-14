/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.primitive;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class ToString extends UnaryPrimitive {
    
    public ToString() {
        super("to_string");
    }

    @Override
	public Object apply (Object value) throws EvaluationException {
    	return value.toString();
    }
}
