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

public class GreaterThan extends BinaryOperator {
    
    public GreaterThan () {
        super(">=");
    }
    
    @Override
	public Object apply (Object arg1, Object arg2) 
            throws EvaluationException {
        if ( arg1 instanceof BigInteger ) {
            BigInteger bi1 = (BigInteger) arg1;
            if ( arg2 instanceof BigInteger ) {
                BigInteger bi2 = (BigInteger) arg2;
                return bi1.compareTo(bi2) >= 0;
            } else if ( arg2 instanceof BigDecimal ) {
                BigDecimal bd2 = (BigDecimal) arg2;
                BigDecimal bd1 = new BigDecimal(bi1);
                return bd1.compareTo(bd2) >= 0;
            } else {
                String msg = "Non numeric argument2";
                throw new EvaluationException(msg);
            }
        } else if ( arg1 instanceof BigDecimal ) {
            BigDecimal bd1 = (BigDecimal) arg1;
            if ( arg2 instanceof BigInteger ) {
                BigInteger bi2 = (BigInteger) arg2;
                BigDecimal bd2 = new BigDecimal(bi2);
                return bd1.compareTo(bd2) >= 0;
            } else if ( arg2 instanceof BigDecimal ) {
                BigDecimal bd2 = (BigDecimal) arg2;
                return bd1.compareTo(bd2) >= 0;
            } else {
                String msg = "Non numeric argument2";
                throw new EvaluationException(msg);
            }
        } else if  ( arg1 instanceof String && arg2 instanceof String ) {
    	    return ((String) arg1).compareTo((String) arg2) >= 0;
        } else {
            String msg = "Non comparable";
            throw new EvaluationException(msg);
        }
    }

}
