/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IInstance;

public class ILPInstance implements IInstance {
    
    public ILPInstance (IClass clazz, Object[] fields) 
            throws EvaluationException {
        this.clazz = clazz;
        this.fields = fields;
        if ( fields.length != clazz.getTotalFieldCount() ) {
            String msg = "Wrong number of initial values for " 
                    + clazz.getName();
            throw new EvaluationException(msg);
        }
    }
    private final IClass clazz;
    private final Object[] fields;
    
    @Override
	public IClass classOf() {
        return clazz;
    }

    @Override
	public Object read(String fieldName) 
            throws EvaluationException {
        int offset = classOf().getOffset(fieldName);
        return fields[offset];
    }

    @Override
	public Object write(String fieldName, Object value) 
            throws EvaluationException {
        int offset = classOf().getOffset(fieldName);
        Object old = fields[offset];
        fields[offset] = value;
        return old;
    }

    @Override
	public Object send(Interpreter interpreter, 
                       String message, 
                       Object[] arguments ) throws EvaluationException {
        return classOf().send(interpreter, this, message, arguments);
    }
    
    // Useful for debug in Eclipse
    @Override
	public String toString () {
        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(clazz.getName());
        for ( String fieldName : clazz.getTotalFieldNames() ) {
            Object value = "?";
            try {
                value = read(fieldName);
            } catch (EvaluationException e) {
                // ignore
            }
            sb.append(":");
            sb.append(fieldName);
            sb.append("=");
            sb.append(value);
        }
        sb.append(">");
        return sb.toString();
    }
}
