/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class Negate extends UnaryOperator {
    
    public Negate () {
        super("!");
    }
    
    @Override
	public Object apply (Object operand) throws EvaluationException {
        if ( operand instanceof Boolean ) {
            Boolean result = ! ((Boolean) operand);
            return result;
        } else {
            return Boolean.FALSE;
        }
    }
}
