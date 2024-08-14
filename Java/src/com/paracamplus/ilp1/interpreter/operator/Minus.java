/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class Minus extends UnaryOperator {
    
    public Minus () {
        super("-");
    }
    
    @Override
	public Object apply (Object arg1) 
            throws EvaluationException {
        if ( arg1 instanceof BigInteger ) {
            BigInteger bi1 = (BigInteger) arg1;
            return BigInteger.ZERO.subtract(bi1);
        } else if ( arg1 instanceof BigDecimal ) {
            BigDecimal bd1 = (BigDecimal) arg1;
            return BigDecimal.ZERO.subtract(bd1);
        } else {
            String msg = "Non numeric argument";
            throw new EvaluationException(msg);
        }
    }
}
