/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

public class TypeOf extends UnaryPrimitive {
    
    public TypeOf() {
        super("type_of");
    }

    @Override
	public Object apply (Object value) throws EvaluationException {
    	if (value instanceof String) {
    		return "String";
    	}
    	else if (value instanceof Boolean) {
    		return "Boolean";
    	}
    	else if (value instanceof BigInteger) {
    		return "Integer";
    	}
    	else if (value instanceof BigDecimal) {
    		return "Float";
    	}
    	else if (value instanceof Invocable) {
    		return "Function";
    	}
    	else {
            String msg = "Unknown value type";
            throw new EvaluationException(msg);
        }
    }
}
