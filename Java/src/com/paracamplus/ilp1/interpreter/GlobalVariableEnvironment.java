/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IPrimitive;

public class GlobalVariableEnvironment implements IGlobalVariableEnvironment {

    public GlobalVariableEnvironment () {
        this.globalVariableEnvironment = new HashMap<>();
    }
    private final Map<String, Object> globalVariableEnvironment;
    
    @Override
	public Object getGlobalVariableValue(String variableName) {
        Object value = globalVariableEnvironment.get(variableName);
        return value;
    }

    @Override
	public void addGlobalVariableValue(String variableName, Object value) {
        globalVariableEnvironment.put(variableName, value);
    }

    @Override
	public void addGlobalVariableValue(IPrimitive primitive) {
        globalVariableEnvironment.put(primitive.getName(), primitive);
    }
    
    @Override
	public void updateGlobalVariableValue(String variableName, Object value) {
        globalVariableEnvironment.put(variableName, value);
    }
}
