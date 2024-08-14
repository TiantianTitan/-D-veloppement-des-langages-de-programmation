/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.interpreter.primitive;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;

public class Throw extends UnaryPrimitive {

    public Throw () {
        super("throw");
    }
    
    @SuppressWarnings("serial")
    public static class ThrownException extends EvaluationException {
        public ThrownException (Object value) {
            super("Throwing value");
            this.value = value;
        }
        private final Object value;
        
        public Object getThrownValue () {
            return value;
        }
    }
    
    @Override
	public Object apply (Object value) throws ThrownException {
        ThrownException exc = new ThrownException(value);
        throw exc;
    }
}
