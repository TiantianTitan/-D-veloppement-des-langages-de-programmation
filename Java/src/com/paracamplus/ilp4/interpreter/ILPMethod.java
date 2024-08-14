/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;
import com.paracamplus.ilp1.interpreter.Function;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;

import com.paracamplus.ilp4.interpreter.interfaces.IMethod;
import com.paracamplus.ilp4.interpreter.interfaces.ISuperCallInformation;

import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;

public class ILPMethod extends Function
implements IMethod, Inamed {
    
    public ILPMethod (String methodName,
                       String definingClassName,
                       IASTvariable[] variables, 
                       IASTexpression body ) {
        super(variables, body, new SuperCallEmptyLexicalEnvironment());
        this.methodName = methodName;
        this.definingClassName = definingClassName;
    }
    private final String methodName;
    private final String definingClassName;
    private IClass definingClass;

    @Override
	public String getName() {
        return methodName;
    }

    public String getDefiningClassName() {
        return definingClassName;
    }
    
    @Override
	public IClass getDefiningClass() {
        return definingClass;
    }
    @Override
	public void setDefiningClass(IClass clazz) {
        definingClass = clazz;
    }
    
    @Override
	public int getMethodArity() {
        return getArity() - 1;
    }
    
    @Override
    public Object apply(Interpreter interpreter, Object[] arguments) 
            throws EvaluationException {
        if ( arguments.length != getArity() ) {
            String msg = "Wrong arity";
            throw new EvaluationException(msg);
        }
        
        ILexicalEnvironment lexenv2 = getClosedEnvironment();
        ISuperCallInformation isci = 
                new SuperCallInformation(arguments, this);
        lexenv2 = ((com.paracamplus.ilp4.interpreter.interfaces.ISuperCallLexicalEnvironment)lexenv2).extend(isci);
        
        IASTvariable[] variables = getVariables();
        for ( int i=0 ; i<arguments.length ; i++ ) {
            lexenv2 = lexenv2.extend(variables[i], arguments[i]);
        }
        return getBody().accept(interpreter, lexenv2);
    }
}
