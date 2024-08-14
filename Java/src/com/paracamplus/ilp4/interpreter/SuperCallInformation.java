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
import com.paracamplus.ilp4.interpreter.interfaces.IMethod;
import com.paracamplus.ilp4.interpreter.interfaces.ISuperCallInformation;

public class SuperCallInformation implements ISuperCallInformation {
    
    public SuperCallInformation (Object[] arguments, IMethod method) {
        assert arguments[0] instanceof IInstance;
        this.arguments = arguments;
        this.method = method;
    }
    private final Object[] arguments;
    private final IMethod method;

    @Override
	public Object[] getArguments() {
        return arguments;
    }

    @Override
	public IMethod getSuperMethod() throws EvaluationException {
        IClass definingClass = method.getDefiningClass();
        if ( "Object".equals(definingClass.getName()) ) {
            String msg = "Cannot invoke super()";
            throw new EvaluationException(msg);
        } else {
            return definingClass.getSuperClass()
                    .getMethodDictionary().get(method.getName());
        }
    }
}
