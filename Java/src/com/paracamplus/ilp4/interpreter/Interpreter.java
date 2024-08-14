/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp4.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.IMethod;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.ISuperCallInformation;
import com.paracamplus.ilp3.interpreter.primitive.Throw.ThrownException;

public class Interpreter extends com.paracamplus.ilp3.interpreter.Interpreter
implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> 
{
    
	public Interpreter (IGlobalVariableEnvironment globalVariableEnvironment,
            IOperatorEnvironment operatorEnvironment,
            IClassEnvironment classEnvironment ) {
		super(globalVariableEnvironment, operatorEnvironment);
		this.classEnvironment = classEnvironment;
	}

    protected IClassEnvironment classEnvironment;
    
    public IClassEnvironment getClassEnvironment () {
        return classEnvironment;
    }

    // 
    @Override 
    public Object visit(com.paracamplus.ilp1.interfaces.IASTprogram iast, ILexicalEnvironment lexenv) throws EvaluationException {
    	return visit((IASTprogram)iast, lexenv);
    }

  
    public Object visit(IASTprogram iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        for ( IASTclassDefinition cd : iast.getClassDefinitions() ) {
            this.visit(cd, lexenv);
        }
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



    // Class-related methods 
    
    public IClass visit(IASTclassDefinition iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        List<IMethod> methods = new Vector<>();
        for ( IASTmethodDefinition md : iast.getProperMethodDefinitions() ) {
            IMethod m = visit(md, lexenv);
            methods.add(m);
        }
        IClass clazz = new ILPClass(
                getClassEnvironment(),
                iast.getName(),
                iast.getSuperClassName(),
                iast.getProperFieldNames(),
                methods.toArray(new IMethod[0]) );
        return clazz;
    }
    
    public IMethod visit(IASTmethodDefinition iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IMethod method = new ILPMethod(
                iast.getMethodName(),
                iast.getDefiningClassName(),
                iast.getVariables(),
                iast.getBody() );
        return method;
    }

    @Override
	public Object visit(IASTinstantiation iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IClass clazz = getClassEnvironment().getILPClass(iast.getClassName());
        List<Object> args = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
            Object value = arg.accept(this, lexenv);
            args.add(value);
        }
        return new ILPInstance(clazz, args.toArray());
    }    
     
    @Override
	public Object visit(IASTfieldRead iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        String fieldName = iast.getFieldName();
        Object target = iast.getTarget().accept(this, lexenv);
        if ( target instanceof ILPInstance ) {
            return ((ILPInstance) target).read(fieldName);
        } else {
            String msg = "Not an ILP instance " + target;
            throw new EvaluationException(msg);
        }
    }
    
    @Override
	public Object visit(IASTfieldWrite iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        String fieldName = iast.getFieldName();
        Object target = iast.getTarget().accept(this, lexenv);
        Object value = iast.getValue().accept(this, lexenv);
        if ( target instanceof ILPInstance ) {
            return ((ILPInstance) target).write(fieldName, value);
        } else {
            String msg = "Not an ILP instance " + target;
            throw new EvaluationException(msg);
        }
    }
    
    @Override
	public Object visit(IASTsend iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        String message = iast.getMethodName();
        Object receiver = iast.getReceiver().accept(this, lexenv);
        List<Object> arguments = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
            Object value = arg.accept(this, lexenv);
            arguments.add(value);
        }
        if ( receiver instanceof ILPInstance ) {
            return ((ILPInstance)receiver).send(
                    this, message, arguments.toArray());
        } else {
            String msg = "Not an ILP instance " + receiver;
            throw new EvaluationException(msg);
        }
    }
    
    @Override
	public Object visit(IASTself iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        return lexenv.getValue(iast);
    }
    
     @Override
	public Object visit(IASTsuper iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
         ISuperCallInformation isci = 
        		 ((com.paracamplus.ilp4.interpreter.interfaces.ISuperCallLexicalEnvironment) lexenv).getSuperCallInformation();
         IMethod supermethod = isci.getSuperMethod();
         return supermethod.apply(this, isci.getArguments());
    }
   
}
