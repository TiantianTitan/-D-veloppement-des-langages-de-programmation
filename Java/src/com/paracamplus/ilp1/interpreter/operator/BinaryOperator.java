/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public abstract class BinaryOperator extends Operator {
    
    public BinaryOperator (String name) {
        super(name);
    }
    
    @Override
	public int getArity() {
        return 2;
    }
    
    public abstract Object apply (Object leftOperand, Object rightOperand) 
            throws EvaluationException;

    @Override
	public Object apply(Object... argument) throws EvaluationException {
        if ( argument.length == getArity() ) {
            return apply(argument[0], argument[1]);
        } else {
            String msg = "Wrong arity for operator " + this.getName();
            throw new EvaluationException(msg);
        }
    }
}
