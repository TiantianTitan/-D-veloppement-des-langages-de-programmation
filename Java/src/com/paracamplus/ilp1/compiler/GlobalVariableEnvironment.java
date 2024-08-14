/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IPrimitive;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class GlobalVariableEnvironment 
implements IGlobalVariableEnvironment {

    public GlobalVariableEnvironment () {
        this.globalVariableEnvironment = new HashMap<>();
        this.globalFunctionEnvironment = new HashMap<>();
    }
    protected final Map<String, String> globalVariableEnvironment;
    protected final Map<String, IPrimitive> globalFunctionEnvironment;

    @Override
	public void addGlobalVariableValue(String variableName, String cName) {
        globalVariableEnvironment.put(variableName, cName);
    }

    @Override
	public void addGlobalFunctionValue(IPrimitive primitive) {
        globalFunctionEnvironment.put(primitive.getName(), primitive);
    }

    @Override
	public String getCName(IASTvariable variable) {
        String cName = globalVariableEnvironment.get(variable.getName());
        if ( cName != null ) {
            return cName;
        } else {
            IPrimitive primitive = 
                    globalFunctionEnvironment.get(variable.getName());
            if ( primitive != null ) {
                return primitive.getCName();
            } else {
                return variable.getMangledName();
            }
        }
    }
    
    @Override
	public boolean isPrimitive(IASTvariable variable) {
        IPrimitive primitive =
                globalFunctionEnvironment.get(variable.getName());
        return ( primitive != null );
    }

    @Override
	public IPrimitive getPrimitiveDescription(IASTvariable variable) {
        IPrimitive primitive =
                globalFunctionEnvironment.get(variable.getName());
        if ( primitive != null ) {
            return primitive;
        } else {
            String msg = "Not a primitive " + variable.getName();
            throw new RuntimeException(msg);
        }
    }
    
    @Override
	public boolean contains(IASTvariable var) {
		return 
				globalVariableEnvironment.containsKey(var.getName()) ||
				globalFunctionEnvironment.containsKey(var.getName());
	}
}
