/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.normalizer;

import com.paracamplus.ilp2.ast.ASTassignment;
import com.paracamplus.ilp2.ast.ASTloop;
import com.paracamplus.ilp2.compiler.ast.ASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.ast.ASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.ast.ASTCprogram;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class NormalizationFactory
extends com.paracamplus.ilp1.compiler.normalizer.NormalizationFactory
implements INormalizationFactory {
    
    @Override
	public IASTCprogram newProgram(
            IASTCfunctionDefinition[] functions,
            IASTexpression expression) {
        return new ASTCprogram(functions,expression); 
    }
   
    @Override
	public IASTCglobalFunctionVariable newGlobalFunctionVariable(String name) {
        return new ASTCglobalFunctionVariable(name);
    }
      
    @Override
	public IASTCfunctionDefinition newFunctionDefinition(
            IASTvariable functionVariable,
            IASTvariable[] variables, 
            IASTexpression body) {
       return new ASTCfunctionDefinition(functionVariable, variables, body);
    }
    

    @Override
	public IASTexpression newAssignment(IASTvariable variable,
                                        IASTexpression value) {
        return new ASTassignment(variable, value);
    }
    
    
    @Override
	public IASTexpression newLoop(IASTexpression condition, IASTexpression body) {
        return new ASTloop(condition, body);
    }


}
