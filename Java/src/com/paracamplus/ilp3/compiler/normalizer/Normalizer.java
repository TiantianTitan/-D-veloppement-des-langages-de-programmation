/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.normalizer;


import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp3.compiler.ast.ASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCnamedLambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp3.interfaces.IASTvisitor;

public class Normalizer 
extends com.paracamplus.ilp2.compiler.normalizer.Normalizer 
implements 
 IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

	  public Normalizer (INormalizationFactory factory) {
	    	super(factory);
	    }

	    public IASTCprogram transform(IASTprogram program) 
	            throws CompilationException {
	        INormalizationEnvironment env = NormalizationEnvironment.EMPTY;
	    
	        IASTfunctionDefinition[] functions = program.getFunctionDefinitions();
	        IASTCfunctionDefinition[] funs = 
	                new IASTCfunctionDefinition[functions.length];
	        for ( IASTfunctionDefinition function : functions ) {
	            IASTCglobalFunctionVariable gfv =
	                    ((INormalizationFactory)factory).newGlobalFunctionVariable(function.getName());
	            env = env.extend(gfv, gfv);
	        }
	        for ( int i=0 ; i<functions.length ; i++ ) {
	            IASTfunctionDefinition function = functions[i];
	            IASTCfunctionDefinition newfunction = visit(function, env);
	            funs[i] = newfunction;
	        }
	        
	        IASTexpression body = program.getBody();
	        IASTexpression newbody = body.accept(this, env);
	        return ((INormalizationFactory)factory).newProgram(funs, newbody);
	    }


    @Override
	public IASTexpression visit(IASTtry iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTexpression newbody = iast.getBody().accept(this, env);
        IASTlambda newcatcher = null;
        IASTlambda catcher = iast.getCatcher();
        if ( catcher != null ){
            newcatcher = (IASTlambda) catcher.accept(this, env);
        }
        IASTexpression newfinallyer = null;
        IASTexpression finallyer = iast.getFinallyer();
        if ( finallyer != null ) {
            newfinallyer = finallyer.accept(this, env);
        }
        return ((INormalizationFactory)factory).newTry(newbody, newcatcher, newfinallyer);
    }

    @Override
	public IASTexpression visit(IASTcodefinitions iast, 
                                INormalizationEnvironment env)
            throws CompilationException {
        IASTnamedLambda[] functions = iast.getFunctions();
        IASTCnamedLambda[] newfunctions = 
                new IASTCnamedLambda[functions.length];
        IASTvariable[] newFunctionVariables = 
                new IASTvariable[functions.length];
        INormalizationEnvironment bodyenv = env;
        for ( int i=0 ; i<functions.length ; i++ ) {
            IASTnamedLambda function = functions[i];
            IASTvariable oldFunctionVar = function.getFunctionVariable();
            IASTvariable newFunctionVar = 
            		((INormalizationFactory)factory).newLocalFunctionVariable(oldFunctionVar.getName());
            newFunctionVariables[i] = newFunctionVar;
            bodyenv = bodyenv.extend(oldFunctionVar, newFunctionVar);
        }
        for ( int i=0 ; i<functions.length ; i++ ) {
            IASTnamedLambda function = functions[i];
            IASTvariable newFunctionVar = newFunctionVariables[i]; 
            INormalizationEnvironment newenv = bodyenv;
            IASTvariable[] variables = function.getVariables();
            IASTvariable[] newvariables = new IASTvariable[variables.length];
            for ( int iv=0 ; iv<variables.length ; iv++ ) {
                IASTvariable variable = variables[iv];
                IASTvariable newvariable = 
                        factory.newLocalVariable(variable.getName());
                newvariables[iv] = newvariable;
                newenv = newenv.extend(variable, newvariable);
            }
            IASTexpression newbody = function.getBody().accept(this, newenv);
            newfunctions[i] = ((INormalizationFactory)factory).newNamedLambda(
                    newFunctionVar, newvariables, newbody);
        }
        IASTexpression newbody = iast.getBody().accept(this, bodyenv);
        return ((INormalizationFactory)factory).newCodefinitions(newfunctions, newbody);
    }


    @Override
	public IASTexpression visit(IASTlambda iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTvariable[] variables = iast.getVariables();
        IASTvariable[] newvariables = new IASTvariable[variables.length];
        INormalizationEnvironment newenv = env;
        for ( int i=0 ; i<variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            IASTvariable newvariable = 
                    factory.newLocalVariable(variable.getName());
            newvariables[i] = newvariable;
            newenv = newenv.extend(variable, newvariable);
        }
        IASTexpression newbody = iast.getBody().accept(this, newenv);
        return ((INormalizationFactory)factory).newLambda(newvariables, newbody);
    }
    
    @Override
	public IASTexpression visit(IASTinvocation iast, 
            INormalizationEnvironment env)
            		throws CompilationException {
    	IASTexpression funexpr = iast.getFunction().accept(this, env);
    	IASTexpression[] arguments = iast.getArguments();
    	IASTexpression[] args = new IASTexpression[arguments.length];
    	for ( int i=0 ; i<arguments.length ; i++ ) {
    		IASTexpression argument = arguments[i];
    		IASTexpression arg = argument.accept(this, env);
    		args[i] = arg;
    	}
    	if ( funexpr instanceof IASTCglobalVariable ) {
    		IASTCglobalVariable f = (IASTCglobalVariable) funexpr;
    		return ((INormalizationFactory)factory).newGlobalInvocation(f, args);
    	} else if ( funexpr instanceof IASTClocalFunctionVariable ) {
    		IASTClocalFunctionVariable f = (ASTClocalFunctionVariable) funexpr;
    		return ((INormalizationFactory)factory).newLocalFunctionInvocation(f, args);
    	} else {
    		return ((INormalizationFactory)factory).newComputedInvocation(funexpr, args);
    	}
    }

}
