/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;

public class LexicalEnvironment implements ILexicalEnvironment {

    public LexicalEnvironment (IASTvariable variable, 
                               Object value,
                               ILexicalEnvironment next ) {
        this.variable = variable;
        this.value = value;
        this.next = next;
    }
    private final IASTvariable variable;
    private Object value;
    private final ILexicalEnvironment next;
    
    @Override
	public IASTvariable getKey() {
        return variable;
    }
    public Object getValue() {
        return value;
    }
    public void updateValue (Object value) {
        this.value = value;
    }
    
    
    @Override
	public boolean isPresent(IASTvariable key) {
        if ( key.getName().equals(getKey().getName()) ) {
            return true;
        } else {
            return getNext().isPresent(key);
        }
    }

    @Override
	public ILexicalEnvironment extend(IASTvariable variable, Object value) {
        return new LexicalEnvironment(variable, value, this);
    }
    
   
    @Override
	public void update(IASTvariable key, Object value) throws EvaluationException {
        if ( key.getName().equals(getKey().getName()) ) {
            updateValue(value);
        } else {
            getNext().update(key, value);
        }
    }

    @Override
	public Object getValue(IASTvariable key) throws EvaluationException {
        if ( key.getName().equals(getKey().getName()) ) {
            return getValue();
        } else {
            return getNext().getValue(key);
        }
    }

    @Override
	public boolean isEmpty() {
        return false;
    }
 
    @Override
	public ILexicalEnvironment getNext() {
        return next;
    }
}
