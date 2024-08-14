/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvariable;


public interface IGlobalVariableEnvironment {
    void addGlobalVariableValue (String variableName, String cName);
    void addGlobalFunctionValue (IPrimitive primitive);
    boolean isPrimitive(IASTvariable variable);
    IPrimitive getPrimitiveDescription(IASTvariable variable);
    String getCName (IASTvariable variable);
	public boolean contains(IASTvariable var);
}
