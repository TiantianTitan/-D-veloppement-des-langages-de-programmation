/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class Or extends BinaryOperator {
    
    public Or () {
        super("|");
    }
    
    @Override
	public Object apply (Object arg1, Object arg2) 
            throws EvaluationException {
        if ( arg1 instanceof Boolean ) {
            Boolean b1 = (Boolean) arg1;
            if ( ! b1.booleanValue() ) {
              // Here, arg1 is false!
              if ( arg2 instanceof Boolean ) {
                Boolean b2 = (Boolean) arg2;
                if ( ! b2.booleanValue() ) {
                  // and here, arg2 is also false!
                  return Boolean.FALSE;
                }
              }
              // Here arg2 cannot be false
              return arg2;
            }
        }
        // Here arg1 cannot be false
        return arg1;
    }
}

// end of Or.java
