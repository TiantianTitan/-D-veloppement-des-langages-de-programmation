/* *****************************************************************
 * ilp4 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp4
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.normalizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationEnvironment;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
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
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class Normalizer 
extends com.paracamplus.ilp3.compiler.normalizer.Normalizer 
implements 
 IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {


    public Normalizer (INormalizationFactory factory,
                       IASTCclassDefinition objectClass ) {
    	super(factory);
        this.field2classes = new HashMap<>();
        this.classes = new HashMap<>();
        classes.put("Object", objectClass);
    }

    private final Map<String, IASTCclassDefinition> classes;
    private final Map<String, IASTCclassDefinition> field2classes;
    
    protected IASTCclassDefinition getClassByName (String className)
            throws CompilationException {
        IASTCclassDefinition cd = classes.get(className);
        if ( cd == null ) {
            String msg = "No class with that name " + className;
            throw new CompilationException(msg);
        }
        return cd;
    }
    
    protected IASTCclassDefinition getClassByFieldName (String fieldName) 
            throws CompilationException {
        IASTCclassDefinition cd = field2classes.get(fieldName);
        if ( cd == null ) {
            String msg = "No class with that field " + fieldName;
            throw new CompilationException(msg);
        }
        return cd;
    }

    public IASTCprogram transform(IASTprogram program) 
            throws CompilationException {
        INormalizationEnvironment env = NormalizationEnvironment.EMPTY;
        IASTclassDefinition[] clazzes = program.getClassDefinitions();
        IASTCclassDefinition[] newclasses = 
                new IASTCclassDefinition[clazzes.length];
        for ( int i=0 ; i<clazzes.length ; i++ ) {
            IASTclassDefinition cd = clazzes[i];
            IASTCclassDefinition newclass = visit(cd, env); 
            newclasses[i] = newclass;
            classes.put(newclass.getName(), newclass);
        }

        IASTfunctionDefinition[] functions = program.getFunctionDefinitions();
        IASTCfunctionDefinition[] funs = 
                new IASTCfunctionDefinition[functions.length];
        for ( IASTfunctionDefinition function : functions ) {
            IASTCglobalFunctionVariable gfv =
            		((INormalizationFactory) factory).newGlobalFunctionVariable(function.getName());
            env = env.extend(gfv, gfv);
        }
        for ( int i=0 ; i<functions.length ; i++ ) {
            IASTfunctionDefinition function = functions[i];
            IASTCfunctionDefinition newfunction = visit(function, env);
            funs[i] = newfunction;
        }
        
        IASTexpression body = program.getBody();
        IASTexpression newbody = body.accept(this, env);
        return ((INormalizationFactory) factory).newProgram(funs, newclasses, newbody);
    }

   
    
    // class related 

    public IASTCclassDefinition visit(IASTclassDefinition iast, 
                                      INormalizationEnvironment env)
            throws CompilationException {
        IASTmethodDefinition[] methods = iast.getProperMethodDefinitions();
        IASTCmethodDefinition[] newmethods =
                new IASTCmethodDefinition[methods.length]; 
        IASTCclassDefinition cd = ((INormalizationFactory) factory).newClassDefinition(
                iast.getName(),
                getClassByName(iast.getSuperClassName()),
                iast.getProperFieldNames(),
                newmethods );
        for ( String fieldName : iast.getProperFieldNames() ) {
            field2classes.put(fieldName, cd);
        }
        for ( int i=0 ; i<methods.length ; i++ ) {
            IASTmethodDefinition method = methods[i];
            newmethods[i] = visit(method, env, cd);
        }
        return cd;
    }

    public IASTCmethodDefinition visit(
            IASTmethodDefinition iast, 
            INormalizationEnvironment env,
            IASTCclassDefinition definingClass )
            throws CompilationException {
        String methodName = iast.getName();
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
        IASTvariable methodVariable = 
        		((INormalizationFactory) factory).newMethodVariable(methodName);
        return ((INormalizationFactory) factory).newMethodDefinition(
                methodVariable, newvariables, newbody, 
                methodName, definingClass );
    }

    @Override
	public IASTexpression visit(IASTinstantiation iast, INormalizationEnvironment env)
            throws CompilationException {
        IASTCclassDefinition cd = getClassByName(iast.getClassName());
        List<Object> args = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
            Object value = arg.accept(this, env);
            args.add(value);
        }
        return ((INormalizationFactory) factory).newInstantiation(
                cd, args.toArray(new IASTexpression[0]));
    }
    
    @Override
	public IASTexpression visit(IASTfieldRead iast, INormalizationEnvironment env)
            throws CompilationException {
        String fieldName = iast.getFieldName();
        IASTCclassDefinition cd = getClassByFieldName(fieldName);
        IASTexpression target = iast.getTarget().accept(this, env);
        return ((INormalizationFactory) factory).newReadField(cd, fieldName, target);
    }

    @Override
	public IASTexpression visit(IASTfieldWrite iast, INormalizationEnvironment env)
            throws CompilationException {
        String fieldName = iast.getFieldName();
        IASTCclassDefinition cd = getClassByFieldName(fieldName);
        IASTexpression target = iast.getTarget().accept(this, env);
        IASTexpression value = iast.getValue().accept(this, env);
        return ((INormalizationFactory) factory).newWriteField(cd, fieldName, target, value);
    }

    @Override
	public IASTexpression visit(IASTsend iast, INormalizationEnvironment env)
            throws CompilationException {
        String message = iast.getMethodName();
        IASTexpression receiver = iast.getReceiver().accept(this, env);
        IASTexpression[] arguments = iast.getArguments();
        IASTexpression[] args = new IASTexpression[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ){
            IASTexpression argument = arguments[i];
            args[i] = argument.accept(this, env);
        }
        return ((INormalizationFactory) factory).newSend(message, receiver, args);
    }

    @Override
	public IASTexpression visit(IASTself iast, INormalizationEnvironment env)
            throws CompilationException {
        return env.renaming(iast);
    }

    @Override
	public IASTexpression visit(IASTsuper iast, INormalizationEnvironment env)
            throws CompilationException {
        return ((INormalizationFactory) factory).newSuper();
    }
}
