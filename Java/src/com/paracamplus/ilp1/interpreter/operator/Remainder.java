/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class Remainder extends BinaryOperator {
    
    public Remainder () {
        super("%");
    }
    
    @Override
	public Object apply (Object arg1, Object arg2) 
            throws EvaluationException {
        if ( arg1 instanceof BigInteger ) {
            BigInteger bi1 = (BigInteger) arg1;
            if ( arg2 instanceof BigInteger ) {
                BigInteger bi2 = (BigInteger) arg2;
                return bi1.divideAndRemainder(bi2)[1];
            } else {
                String msg = "Non integer argument2";
                throw new EvaluationException(msg);
            }
        } else {
            String msg = "Non integer argument1";
            throw new EvaluationException(msg);
        }
    }
}
