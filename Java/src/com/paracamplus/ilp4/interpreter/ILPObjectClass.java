/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.IMethod;

public class ILPObjectClass extends ILPAbstractClass {

    ILPObjectClass (IClassEnvironment classEnvironment) 
            throws EvaluationException {
        super(classEnvironment, "Object", null, new String[0], new IMethod[0]);
    }
    
    @Override
	public IClass getSuperClass() throws EvaluationException {
        String msg = "Object has no super class";
        throw new EvaluationException(msg);
    }
    
    @Override
	public String[] getProperFieldNames () {
        return properFieldNames;
    }
    private final static String[] properFieldNames = new String[0];
    
    @Override
	public int getTotalFieldCount() {
        return 0;
    }
    
    @Override
	public String[] getTotalFieldNames() {
        return properFieldNames;
    }
    
    @Override
	public int getOffset(String fieldName) throws EvaluationException {
        String msg = "Object has no field " + fieldName;
        throw new EvaluationException(msg);
    }
}
