/* *****************************************************************
 * ilp3 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp3
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.interpreter;

import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp3.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interpreter.Function;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IFunction;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp3.interpreter.primitive.Throw.ThrownException;

public class Interpreter extends com.paracamplus.ilp2.interpreter.Interpreter
implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    
	 public Interpreter(IGlobalVariableEnvironment globalVariableEnvironment,
				IOperatorEnvironment operatorEnvironment) {
			super(globalVariableEnvironment, operatorEnvironment);
		}


    @Override 
    public Object visit(com.paracamplus.ilp1.interfaces.IASTprogram iast, ILexicalEnvironment lexenv) throws EvaluationException {
    	return visit((IASTprogram)iast, lexenv);
    }

    public Object visit(IASTprogram iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        for ( IASTfunctionDefinition fd : iast.getFunctionDefinitions() ) {
            Object f = this.visit(fd, lexenv);
            String v = fd.getName();
            getGlobalVariableEnvironment().addGlobalVariableValue(v, f);
        }
        try {
            return iast.getBody().accept(this, lexenv);
        } catch (ThrownException exc) {
            return exc.getThrownValue();
        } catch (Exception exc) {
            return exc;
        }
    }
   
    // 

    @Override
	public Object visit(IASTlambda iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IFunction fun = new Function(iast.getVariables(),
                                     iast.getBody(),
                                     lexenv);
        return fun;
    }
    
    @Override
	public Object visit(IASTcodefinitions iast, ILexicalEnvironment lexenv)
            throws EvaluationException {
        IASTnamedLambda[] functions = iast.getFunctions();
        ILexicalEnvironment lexenv2 = lexenv;
        for ( IASTnamedLambda fun : functions ) {
            IASTvariable variable = fun.getFunctionVariable();
            lexenv2 = lexenv2.extend(variable, null);
        }
        for ( IASTnamedLambda fun : functions ) {
            Object f = fun.accept(this, lexenv2);
            IASTvariable variable = fun.getFunctionVariable();
            lexenv2.update(variable, f);
        }
        IASTexpression body = iast.getBody();
        Object result = body.accept(this, lexenv2);
        return result;
    }
    

    @Override
	public Object visit(IASTtry iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        Object result = Boolean.FALSE;
        IFunction fcatcher = null;
        IASTlambda catcher = iast.getCatcher();
        if ( null != catcher ) {
            fcatcher = (IFunction) catcher.accept(this, lexenv);
        }
        try {
            result = iast.getBody().accept(this, lexenv);
        } catch (ThrownException exc) {
            if ( null != fcatcher ) {
                Object value = exc.getThrownValue();
                fcatcher.apply(this, new Object[]{ value });
            } else {
                throw exc;
            }
        } catch (EvaluationException exc) {
            if ( null != fcatcher ) {
                fcatcher.apply(this, new Object[]{ exc });
            } else {
                throw exc;
            }
        } catch (Exception exc) {
            if ( null != fcatcher ) {
                EvaluationException e = new EvaluationException(exc);
                fcatcher.apply(this, new Object[]{ e });
            } else {
                throw exc;
            }
        } finally {
            IASTexpression finallyer = iast.getFinallyer();
            if ( null != finallyer ) {
                finallyer.accept(this, lexenv);
            }
        }
        return result;
    }

}
