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

public class SuperCallEmptyLexicalEnvironment implements ISuperCallLexicalEnvironment {
    
    public SuperCallEmptyLexicalEnvironment() {}

    @Override
	public boolean isPresent(IASTvariable key) {
        return false;
    }

    @Override
	public IASTvariable getKey() throws EvaluationException {
        throw new EvaluationException("Really empty environment");
    }

    @Override
	public Object getValue(IASTvariable key) throws EvaluationException {
        throw new EvaluationException("No such variable " + key.getName());
    }

    @Override
	public void update(IASTvariable key, Object value)
            throws EvaluationException {
        throw new EvaluationException("Empty environment");
    }

    @Override
	public ISuperCallInformation getSuperCallInformation()
            throws EvaluationException {
        String msg = "No such information";
        throw new EvaluationException(msg);
    }

    @Override
	public boolean isEmpty() {
        return true;
    }

    @Override
	public ISuperCallLexicalEnvironment extend(IASTvariable variable, Object value) {
        return new SuperCallLexicalEnvironment(variable, value, this);
    }

    @Override
	public ISuperCallLexicalEnvironment extend(ISuperCallInformation isci) {
        // Dependence on SuperCallInformationLexicalEnvironment
        return new SuperCallInformationLexicalEnvironment(isci, this);
    }

    @Override
	public ISuperCallLexicalEnvironment getNext() throws EvaluationException {
        throw new EvaluationException("Completely empty environment");
    }
}
