/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.IMethod;
import com.paracamplus.ilp1.interpreter.interfaces.IPrimitive;
import com.paracamplus.ilp1.interpreter.primitive.Newline;
import com.paracamplus.ilp1.interpreter.primitive.Print;

import com.paracamplus.ilp1.interpreter.Interpreter;


public class ClassEnvironment implements IClassEnvironment {
    
    public ClassEnvironment (Writer out) throws EvaluationException {
        this.clazzes = new HashMap<>();
        IClass objectClass = new ILPObjectClass(this);
        initializeClassEnvironment(objectClass, out);
    }
    private final Map<String, IClass> clazzes;

    @Override
	public IClass getILPClass(String name) throws EvaluationException {
        IClass clazz = clazzes.get(name);
        if ( clazz == null ) {
            String msg = "No such class " + name;
            throw new EvaluationException(msg);
        }
        return clazz;
    }

    @Override
	public void addILPClass(IClass clazz) {
        clazzes.put(clazz.getName(), clazz);
    }
    
    //
    
    protected void initializeClassEnvironment(IClass clazz, Writer out) {
        addPrimitiveAsMethod(clazz, new Print(out));
        addPrimitiveAsMethod(clazz, new Newline(out));
    }

    protected void addPrimitiveAsMethod (final IClass clazz, final IPrimitive primitive) {
        IMethod method = new IMethod() {
            @Override
			public String getName() {
                return primitive.getName();
            }
            @Override
			public IClass getDefiningClass() {
                return clazz;
            }
            @Override
			public void setDefiningClass (IClass clazz) {
                String msg = "Hard coded immutable class";
                throw new RuntimeException(msg);
            }
            @Override
			public int getMethodArity() {
                return primitive.getArity() - 1;
            }
            @Override
			public int getArity() {
                return primitive.getArity();
            }
            @Override
			public Object apply (Interpreter interpreter, Object[] arguments) 
            throws EvaluationException {
                return primitive.apply(interpreter, arguments);
            }
        };
        clazz.getMethodDictionary().put(primitive.getName(), method);
    }
}
