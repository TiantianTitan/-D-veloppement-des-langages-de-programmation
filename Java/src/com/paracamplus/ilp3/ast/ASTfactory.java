/* *****************************************************************
 * ilp3 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp3
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.ast;


import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp3.interfaces.IASTfactory;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTtry;

public class ASTfactory extends com.paracamplus.ilp2.ast.ASTfactory
implements IASTfactory{

    @Override
	public IASTprogram newProgram(IASTfunctionDefinition[] functions, 
                                  IASTexpression expression) {
        return new ASTprogram(functions, expression);
    }
    
    @Override
	public IASTtry newTry (IASTexpression body,
                           IASTlambda catcher,
                           IASTexpression finallyer ) {
        return new ASTtry(body, catcher, finallyer);
    }
    
    @Override
	public IASTlambda newLambda (IASTvariable[] variables,
                                 IASTexpression body ) {
        return new ASTlambda(variables, body);
    }
    
    @Override
	public IASTnamedLambda newNamedLambda (
            IASTvariable functionVariable,
            IASTvariable[] variables,
            IASTexpression body ) {
        return new ASTnamedLambda(functionVariable, variables, body);
    }
    
    @Override
	public IASTcodefinitions newCodefinitions (
            IASTnamedLambda[] functions,
            IASTexpression body ) {
        return new ASTcodefinitions(functions, body);
    }
    
   
}
