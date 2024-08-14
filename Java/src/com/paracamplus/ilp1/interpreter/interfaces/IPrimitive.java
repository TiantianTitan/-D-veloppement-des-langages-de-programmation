/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.Interpreter;

public interface IPrimitive extends Invocable {
    String getName();
    
    default Object erroneousApply() throws EvaluationException {
        String msg = "Incorrect arity " + this.getName();
        throw new EvaluationException(msg);
    }
    default Object apply() throws EvaluationException {
        return erroneousApply();
    }
    default Object apply(Object arg1) throws EvaluationException {
        return erroneousApply();
    }
    default Object apply(Object arg1, Object arg2) throws EvaluationException {
        return erroneousApply();
    }
    default Object apply(Object arg1, Object arg2, Object arg3) 
            throws EvaluationException {
        return erroneousApply();
    }
    
    @Override
	default Object apply(Interpreter interpreter, Object[] argument) 
            throws EvaluationException {
        if ( argument.length == getArity() ) {
            switch (getArity()) {
            case 0: {
                return apply();
            }
            case 1: {
                return apply(argument[0]);
            }
            case 2: {
                return apply(argument[0], argument[1]);
            }
            case 3: {
                return apply(argument[0], argument[1], argument[2]);
            }
            default: {
                String msg = "Unhandled primitive arity " + this.getName();
                throw new EvaluationException(msg);
            }
            }
        } else {
            String msg = "Wrong arity for operator " + this.getName();
            throw new EvaluationException(msg);
        }
    }
}
