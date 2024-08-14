/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter.interfaces;

import java.util.Map;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.Interpreter;

public interface IClass {
    String getName();
    IClass getSuperClass() throws EvaluationException;
    // static IClass getClassByName(String className) 
    //   throws EvaluationException; 
    String[] getProperFieldNames();
    String[] getTotalFieldNames();
    int getTotalFieldCount();
    int getOffset(String fieldName) throws EvaluationException;
    Map<String,IMethod> getMethodDictionary();
    Object send(Interpreter interpreter,
                IInstance receiver, 
                String message, 
                Object[] arguments ) throws EvaluationException;
}
