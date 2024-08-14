/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IFunction;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;

public class Function implements IFunction {
    
    public Function (IASTvariable[] variables, 
                     IASTexpression body, 
                     ILexicalEnvironment lexenv) {
        this.variables = variables;
        this.body = body;
        this.lexenv = lexenv;
    }
    private final IASTvariable[] variables;
    private final IASTexpression body;
    private final ILexicalEnvironment lexenv;

    @Override
	public int getArity() {
        return variables.length;
    }
    public IASTvariable[] getVariables() {
        return variables;
    }
    public IASTexpression getBody() {
        return body;
    }
    
    protected ILexicalEnvironment getClosedEnvironment() {
        return lexenv;
    }

    @Override
	public Object apply(Interpreter interpreter, Object[] argument) 
            throws EvaluationException {
        if ( argument.length != getArity() ) {
            String msg = "Wrong arity";
            throw new EvaluationException(msg);
        }
        
        ILexicalEnvironment lexenv2 = getClosedEnvironment();
        IASTvariable[] variables = getVariables();
        for ( int i=0 ; i<argument.length ; i++ ) {
            lexenv2 = lexenv2.extend(variables[i], argument[i]);
        }
        return getBody().accept(interpreter, lexenv2);
    }
}
