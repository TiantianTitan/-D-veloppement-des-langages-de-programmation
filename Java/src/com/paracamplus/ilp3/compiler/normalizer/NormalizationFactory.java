/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.normalizer;

import com.paracamplus.ilp3.ast.ASTtry;
import com.paracamplus.ilp3.compiler.ast.ASTCcodefinitions;
import com.paracamplus.ilp3.compiler.ast.ASTClambda;
import com.paracamplus.ilp3.compiler.ast.ASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.ast.ASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.ast.ASTCnamedLambda;
import com.paracamplus.ilp3.compiler.ast.ASTCprogram;
import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCnamedLambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class NormalizationFactory
extends com.paracamplus.ilp2.compiler.normalizer.NormalizationFactory
implements INormalizationFactory {
    
    
    @Override
	public IASTCprogram newProgram(
            IASTCfunctionDefinition[] functions,
            IASTexpression expression) {
        return new ASTCprogram(functions, expression); 
    }


    public String newGlobalClosureName() {
        String newName = "ilpclosure" + count.incrementAndGet();
        return newName;
    }
  
        
    @Override
	public IASTClocalFunctionInvocation newLocalFunctionInvocation(
            IASTClocalFunctionVariable function,
            IASTexpression[] arguments) {
        return new ASTClocalFunctionInvocation(function, arguments);
    }
    
    
    @Override
	public IASTexpression newTry(
            IASTexpression body, 
            IASTlambda catcher,
            IASTexpression finallyer) {
        return new ASTtry(body, catcher, finallyer);
    }
    
    @Override
	public IASTClambda newLambda(IASTvariable[] variables,
                                 IASTexpression body) {
        String closureName = newGlobalClosureName();
        return new ASTClambda(closureName, variables, body);
    }
    
    @Override
	public IASTCnamedLambda newNamedLambda(
            IASTvariable functionVariable,
            IASTvariable[] variables, 
            IASTexpression body) {
        return new ASTCnamedLambda(functionVariable, variables, body);
    }
    
    @Override
	public IASTCcodefinitions newCodefinitions(
            IASTCnamedLambda[] functions,
            IASTexpression body) {
        return new ASTCcodefinitions(functions, body);
    }


    @Override
	public IASTClocalFunctionVariable newLocalFunctionVariable(String name) {
        String newName = name + count.incrementAndGet();
        return new ASTClocalFunctionVariable(newName);
    }
    
  

}
