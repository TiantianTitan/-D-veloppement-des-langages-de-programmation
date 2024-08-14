/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.primitive;

import java.io.IOException;
import java.io.Writer;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class Print extends UnaryPrimitive {
    
    public Print(Writer out) {
        super("print");
        this.out = out;
    }
    private final Writer out;
        
    @Override
	public Object apply (Object value) throws EvaluationException {
        try {
            out.append(value.toString());
        } catch (IOException e) {
            throw new EvaluationException(e);
        }
        return Boolean.FALSE;
    }
}
