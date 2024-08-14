/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;



public class ASTfactory extends com.paracamplus.ilp1.ast.ASTfactory 
	implements IASTfactory{

    @Override
	public IASTprogram newProgram(IASTfunctionDefinition[] functions, 
                                  IASTexpression expression) {
        return new ASTprogram(functions, expression);
    }
    

    @Override
	public IASTassignment newAssignment(IASTvariable variable,
                                        IASTexpression value) {
        return new ASTassignment(variable, value);
    }


    @Override
	public IASTloop newLoop(IASTexpression condition, IASTexpression body) {
        return new ASTloop(condition, body);
    }

    @Override
	public IASTfunctionDefinition newFunctionDefinition(
            IASTvariable functionVariable,
            IASTvariable[] variables, 
            IASTexpression body) {
        return new ASTfunctionDefinition(functionVariable, variables, body);
    }

}
