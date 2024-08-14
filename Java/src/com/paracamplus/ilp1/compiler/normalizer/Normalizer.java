/* *****************************************************************
 * ilp1 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp1
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class Normalizer implements 
 IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

    public Normalizer (INormalizationFactory factory) {
        this.factory = factory;
        this.globalVariables = new HashSet<>();
    }
    protected final INormalizationFactory factory;
    protected final Set<IASTvariable> globalVariables;
   

    public IASTCprogram transform(IASTprogram program) 
            throws CompilationException {
        INormalizationEnvironment env = NormalizationEnvironment.EMPTY;
    
        IASTexpression body = program.getBody();
        IASTexpression newbody = body.accept(this, env);
        return factory.newProgram(newbody);
    }

    @Override
	public IASTexpression visit(IASTalternative iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTexpression c = iast.getCondition().accept(this, env);
        IASTexpression t = iast.getConsequence().accept(this, env);
        if ( iast.isTernary() ) {
            IASTexpression a = iast.getAlternant().accept(this, env);
            return factory.newAlternative(c, t, a);
        } else {
            IASTexpression whatever = factory.newBooleanConstant("false");
            return factory.newAlternative(c, t, whatever);
        }
    }
    
    @Override
	public IASTexpression visit(IASTboolean iast, INormalizationEnvironment env)
            throws CompilationException {
        return iast;
    }
    
    @Override
	public IASTexpression visit(IASTinteger iast, INormalizationEnvironment env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTfloat iast, INormalizationEnvironment env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTstring iast, INormalizationEnvironment env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTsequence iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTexpression[] expressions = iast.getExpressions();
        IASTexpression[] exprs = new IASTexpression[expressions.length];
        for ( int i=0 ; i< expressions.length ; i++ ) {
            exprs[i] = expressions[i].accept(this, env);
        }
        if ( iast.getExpressions().length == 1 ) {
            return exprs[0];
        } else {
            return factory.newSequence(exprs);
        }
    }

    @Override
	public IASTvariable visit(IASTvariable iast, INormalizationEnvironment env)
            throws CompilationException {
        try {
            return env.renaming(iast);
        } catch (NoSuchLocalVariableException exc) {
            for ( IASTvariable gv : globalVariables ) {
                if ( iast.getName().equals(gv.getName()) ) {
                    return gv;
                }
            }
            IASTvariable gv = factory.newGlobalVariable(iast.getName());
            globalVariables.add(gv);
            return gv;
        }
    }
    

    @Override
	public IASTexpression visit(IASTunaryOperation iast, INormalizationEnvironment env)
            throws CompilationException {
        IASToperator operator = iast.getOperator();
        IASTexpression operand = iast.getOperand().accept(this, env);
        return factory.newUnaryOperation(operator, operand);
    }

    @Override
	public IASTexpression visit(IASTbinaryOperation iast, INormalizationEnvironment env)
            throws CompilationException {
        IASToperator operator = iast.getOperator();
        IASTexpression left = iast.getLeftOperand().accept(this, env);
        IASTexpression right = iast.getRightOperand().accept(this, env);
        return factory.newBinaryOperation(operator, left, right);
    }
    
    @Override
	public IASTexpression visit(IASTblock iast, INormalizationEnvironment env)
            throws CompilationException {
        INormalizationEnvironment newenv = env;
        IASTbinding[] bindings = iast.getBindings();
        IASTCblock.IASTCbinding[] newbindings = 
                new IASTCblock.IASTCbinding[bindings.length];
        for ( int i=0 ; i<bindings.length ; i++ ) {
            IASTbinding binding = bindings[i];
            IASTexpression expr = binding.getInitialisation();
            IASTexpression newexpr = expr.accept(this, env);
            IASTvariable variable = binding.getVariable();
            IASTvariable newvariable = 
                    factory.newLocalVariable(variable.getName());
            newenv = newenv.extend(variable, newvariable);
            newbindings[i] = factory.newBinding(newvariable, newexpr);
        }
        IASTexpression newbody = iast.getBody().accept(this, newenv);
        return factory.newBlock(newbindings, newbody);
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
    		return factory.newGlobalInvocation(f, args);
    	} else {
    		return factory.newComputedInvocation(funexpr, args);
    	}
    }
    
 
}
