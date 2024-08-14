/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;




public interface IGlobalVariableEnvironment {
    Object getGlobalVariableValue (String variableName);
    void addGlobalVariableValue (String variableName, Object value);
    void addGlobalVariableValue (IPrimitive primitive);
    void updateGlobalVariableValue (String variableName, Object value);
}
