/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.ISuperCallLexicalEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.ISuperCallInformation;

public class SuperCallInformationLexicalEnvironment 
implements ISuperCallLexicalEnvironment {
    
    public SuperCallInformationLexicalEnvironment (
            ISuperCallInformation isci,
            ISuperCallLexicalEnvironment next ) {
        this.isci = isci;
        this.next = next;
    }
    private final ISuperCallInformation isci;
    private final ISuperCallLexicalEnvironment next;
    
    @Override
	public ISuperCallInformation getSuperCallInformation() {
        return isci;
    }

    @Override
	public ISuperCallLexicalEnvironment getNext() {
        return next;
    }

    @Override
	public boolean isPresent(IASTvariable key) {
        return getNext().isPresent(key);
    }

    @Override
	public IASTvariable getKey() throws EvaluationException {
        String msg = "No key on " + this.getClass().getName();
        throw new EvaluationException(msg);
    }

    @Override
	public Object getValue(IASTvariable key) throws EvaluationException {
        String msg = "No value on " + this.getClass().getName();
        throw new EvaluationException(msg);
    }

    @Override
	public void update(IASTvariable key, Object value)
            throws EvaluationException {
        String msg = "Cannot update " + this.getClass().getName();
        throw new EvaluationException(msg);
    }

    @Override
	public boolean isEmpty() {
        return getNext().isEmpty();
    }

    @Override
	public ISuperCallLexicalEnvironment extend(ISuperCallInformation isci) {
        return new SuperCallInformationLexicalEnvironment(isci, this);
    }

    @Override
	public ISuperCallLexicalEnvironment extend(IASTvariable variable, Object value) {
        return  new SuperCallLexicalEnvironment(variable, value, this);
    }
}
