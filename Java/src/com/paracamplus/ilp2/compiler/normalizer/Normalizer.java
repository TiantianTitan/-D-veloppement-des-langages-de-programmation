/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.normalizer;


import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationEnvironment;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class Normalizer 
extends com.paracamplus.ilp1.compiler.normalizer.Normalizer 
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
	public IASTexpression visit(IASTassignment iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTvariable variable = iast.getVariable();
        IASTvariable newvariable = visit(variable, env);
        IASTexpression expression = iast.getExpression();
        IASTexpression newexpression = expression.accept(this, env);
        return ((INormalizationFactory)factory).newAssignment(newvariable, newexpression);
    }

    
    public IASTCfunctionDefinition visit(
            IASTfunctionDefinition iast,
            INormalizationEnvironment env) throws CompilationException {
        String functionName = iast.getName();
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
        IASTvariable functionVariable = 
        		((INormalizationFactory)factory).newGlobalFunctionVariable(functionName);
        return ((INormalizationFactory)factory).newFunctionDefinition(
                functionVariable, newvariables, newbody);
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
    	} else {
    		return ((INormalizationFactory)factory).newComputedInvocation(funexpr, args);
    	}
    }
    

    @Override
	public IASTexpression visit(IASTloop iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTexpression newcondition = iast.getCondition().accept(this, env);
        IASTexpression newbody = iast.getBody().accept(this, env);
        return ((INormalizationFactory)factory).newLoop(newcondition, newbody);
    }
    
}
