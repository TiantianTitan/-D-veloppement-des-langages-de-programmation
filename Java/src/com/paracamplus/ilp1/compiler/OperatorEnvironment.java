/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASToperator;

public class OperatorEnvironment implements IOperatorEnvironment {

    public OperatorEnvironment () {
        this.unaryOperatorEnvironment = new HashMap<>();
        this.binaryOperatorEnvironment = new HashMap<>();
    }
    private final Map<String, String> unaryOperatorEnvironment;
    private final Map<String, String> binaryOperatorEnvironment;
    
    @Override
	public String getUnaryOperator(IASToperator operator)
            throws CompilationException {
        String meaning = unaryOperatorEnvironment.get(operator.getName());
        if ( meaning != null ) {
            return meaning;
        } else {
            String msg = "No such operator " + operator.getName();
            throw new CompilationException(msg);
        }
    }

    @Override
	public String getBinaryOperator(IASToperator operator)
            throws CompilationException {
        String meaning = binaryOperatorEnvironment.get(operator.getName());
        if ( meaning != null ) {
            return meaning;
        } else {
            String msg = "No such operator " + operator.getName();
            throw new CompilationException(msg);
        }
    }

    @Override
	public void addUnaryOperator(String operator, String cName) 
            throws CompilationException {
        unaryOperatorEnvironment.put(operator, cName);
    }
    
    @Override
	public void addBinaryOperator(String operator, String cName) 
            throws CompilationException {
        binaryOperatorEnvironment.put(operator, cName);
    }
}
