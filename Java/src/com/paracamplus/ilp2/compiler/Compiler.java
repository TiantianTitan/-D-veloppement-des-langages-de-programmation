/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.ReturnDestination;
import com.paracamplus.ilp1.compiler.VoidDestination;
import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IPrimitive;
import com.paracamplus.ilp2.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp2.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp2.compiler.normalizer.Normalizer;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.compiler.interfaces.IASTCvisitor;


public class Compiler extends com.paracamplus.ilp1.compiler.Compiler 
implements IASTCvisitor<Void, Compiler.Context, CompilationException>{
    
 
    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}
    
    //

    public IASTCprogram normalize(IASTprogram program) 
            throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }

    @Override
    public String compile(com.paracamplus.ilp1.interfaces.IASTprogram program) throws CompilationException  {
    	return compile((IASTprogram)program);
    }

    public String compile(IASTprogram program) 
            throws CompilationException {
        
        IASTCprogram newprogram = normalize(program);
        newprogram = ((IASTCprogram) optimizer.transform(newprogram));

        GlobalVariableCollector gvc = new GlobalVariableCollector();
        Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
        newprogram.setGlobalVariables(gvs);
        
        FreeVariableCollector fvc = new FreeVariableCollector(newprogram);
        newprogram = (fvc.analyze());
      
        Context context = new Context(NoDestination.NO_DESTINATION);
        StringWriter sw = new StringWriter();
        try {
            out = new BufferedWriter(sw);
            visit(newprogram, context);
            out.flush();
        } catch (IOException exc) {
            throw new CompilationException(exc);
        }
        return sw.toString();
    }

    //
    
    public Void visit(IASTCprogram iast, Context context)
            throws CompilationException {
        emit(cProgramPrefix);
        
        emit(cGlobalVariablesPrefix);
        for ( IASTCglobalVariable gv : iast.getGlobalVariables() ) {
            emit("ILP_Object ");
            emit(gv.getMangledName());
            emit(";\n");
        }
        emit(cGlobalVariablesSuffix);
        
        emit(cPrototypesPrefix);
        Context c = context.redirect(NoDestination.NO_DESTINATION);
        for ( IASTfunctionDefinition ifd : iast.getFunctionDefinitions() ) {
            this.emitPrototype(ifd, c);
        }

        emit(cFunctionsPrefix);
        for ( IASTfunctionDefinition ifd : iast.getFunctionDefinitions() ) {
            this.visit(ifd, c);
            emitClosure(ifd, c);
        }

        emit(cFunctionsSuffix);
        
        
        emit(cBodyPrefix);
        Context cr = context.redirect(ReturnDestination.RETURN_DESTINATION);
        iast.getBody().accept(this, cr);
        emit(cBodySuffix);
        
        emit(cProgramSuffix);
        return null;
    }


    protected String cPrototypesPrefix = ""
            + "/* Global prototypes */ \n";
    
    protected String cFunctionsPrefix = "\n"
            + "/* Global functions */ \n";
    protected String cFunctionsSuffix = "\n";
    
    @Override
	public Void visit(IASTvariable iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTClocalVariable ) {
            return visit((IASTClocalVariable) iast, context);
        } else if ( iast instanceof IASTCglobalFunctionVariable ) {
            return visit((IASTCglobalFunctionVariable) iast, context);
        } else {
            return visit((IASTCglobalVariable) iast, context);
        }
    }
    

    @Override
	public Void visit(IASTCglobalFunctionVariable iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("(ILP_Object)&" + globalVariableEnvironment.getCName(iast));
        emit("_closure_object; \n");
        return null;
    }
    
    @Override
	public Void visit(IASTassignment iast, Context context)
            throws CompilationException {
        if ( iast.getVariable() instanceof IASTClocalVariable ) {
            return visitLocalAssignment(iast, context);
        } else {
            return visitNonLocalAssignment(iast, context);
        }
    }
    
    private Void visitLocalAssignment(IASTassignment iast, Context context) 
            throws CompilationException {
        IASTvariable tmp1 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        Context c1 = context.redirect(new AssignDestination(tmp1));
        iast.getExpression().accept(this, c1);
        // Cast ensured by visit(IASTassignment...)
        IASTClocalVariable lv = (IASTClocalVariable) iast.getVariable();
        emit(context.destination.compile());
        emit("(");
        if ( lv.isClosed() ) {
            emit("ILP_SetBoxedValue(");
            emit(lv.getMangledName());
            emit(", ");
            emit(tmp1.getMangledName());
            emit(")");
        } else {
            emit(lv.getMangledName());
            emit(" = ");
            emit(tmp1.getMangledName());
        }
        emit("); \n} \n");
        return null;
    }
    
    
  
    
    private Void visitNonLocalAssignment(IASTassignment iast, Context context) 
            throws CompilationException {
        IASTvariable tmp1 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        Context c1 = context.redirect(new AssignDestination(tmp1));
        iast.getExpression().accept(this, c1);
        emit(context.destination.compile());
        emit("(");
        emit(iast.getVariable().getMangledName());
        emit(" = ");
        emit(tmp1.getMangledName());
        emit("); \n} \n");
        return null;
    }
   

    protected void emitPrototype(IASTfunctionDefinition iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCfunctionDefinition ) {
            emitPrototype((IASTCfunctionDefinition)iast, context);
        } else {
            throw new CompilationException("should not occur");
        }
    }
    protected void emitPrototype(IASTCfunctionDefinition iast, Context context)
            throws CompilationException {
        emit("ILP_Object ");
        emit(iast.getCName());
        emit("(ILP_Closure ilp_useless\n");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit(",\n    ILP_Object ");
            emit(variable.getMangledName());
        }
        emit(");\n");
    }
    protected Void visit(IASTfunctionDefinition iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCfunctionDefinition ) {
            return visit((IASTCfunctionDefinition)iast, context);
        } else {
            throw new CompilationException("should not occur");
        }
    }
    public Void visit(IASTCfunctionDefinition iast, Context context)
            throws CompilationException {
        emit("\nILP_Object ");
        emit(iast.getCName());
        emit("(ILP_Closure ilp_useless\n");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit(",\n    ILP_Object ");
            emit(variable.getMangledName());
        }
        emit(") {\n");
        for ( IASTvariable variable : variables ) {
            try {
                // Cast ensured by normalizer:
                IASTClocalVariable lv = (IASTClocalVariable) variable;
                if ( lv.isClosed() ) {
                    emit(lv.getMangledName());
                    emit(" = ");
                    emit("ILP_Value2Box(");
                    emit(lv.getMangledName());
                    emit("); \n");
                }
            } catch (ClassCastException exc) {
                throw new RuntimeException("Should not occur");
            }
        }
        Context c = context.redirect(ReturnDestination.RETURN_DESTINATION);
        iast.getBody().accept(this, c);
        emit("}\n");
        return null;
    }
    protected void emitClosure(IASTfunctionDefinition iast, Context context)
            throws CompilationException {
        emit("struct ILP_Closure ");
        emit(iast.getMangledName());
        emit("_closure_object = { \n");
        emit("   &ILP_object_Closure_class, \n");
        emit("   { { ilp__");
        emit(iast.getMangledName());
        emit(", \n");
        emit("       " + iast.getVariables().length + ", \n");
        emit("       { NULL } } } \n");
        emit("}; \n");      
    }
    
   
    
    @Override
	public Void visit(IASTloop iast, Context context)
            throws CompilationException {
        emit("while ( 1 ) { \n");
        IASTvariable tmp = context.newTemporaryVariable();
        emit("  ILP_Object " + tmp.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmp));
        iast.getCondition().accept(this, c);
        emit("  if ( ILP_isEquivalentToTrue(");
        emit(tmp.getMangledName());
        emit(") ) {\n");
        Context cb = context.redirect(VoidDestination.VOID_DESTINATION);
        iast.getBody().accept(this, cb);
        emit("\n} else { \n");
        emit("    break; \n");
        emit("\n}\n}\n");
        whatever.accept(this, context);
        return null;
    }
    
    
    @Override
	public Void visit(IASTinvocation iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCglobalInvocation ) {
            return visit((IASTCglobalInvocation) iast, context);
        } else if ( iast instanceof IASTCcomputedInvocation ) {
            return visit((IASTCcomputedInvocation) iast, context);
        } else {
            return visitGeneralInvocation(iast, context);
        }
    }

    public Void visit(IASTCglobalInvocation iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTexpression[] arguments = iast.getArguments();
        IASTvariable[] tmps = new IASTvariable[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("  ILP_Object " + tmp.getMangledName() + "; \n");
            tmps[i] = tmp;
        }
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTexpression expression = arguments[i];
            IASTvariable tmp = tmps[i];
            Context c = context.redirect(new AssignDestination(tmp));
            expression.accept(this, c);
        }
        emit(context.destination.compile());
        if ( globalVariableEnvironment.isPrimitive(iast.getFunction()) ) {
            // Should check arity statically!
            IPrimitive fun = globalVariableEnvironment
                    .getPrimitiveDescription(iast.getFunction());
            emit(fun.getCName());
            emit("(");
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTvariable tmp = tmps[i];
                emit(tmp.getMangledName());
                if ( i < arguments.length-1 ) {
                    emit(", ");
                }
            }
        } else if (iast.getFunction() instanceof IASTCglobalFunctionVariable) {
            // Should check arity statically!
            emit("ilp__" + iast.getFunction().getMangledName());
            emit("(NULL ");
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTvariable tmp = tmps[i];
                emit(", ");
                emit(tmp.getMangledName());
            }
        } else {
            emit("ILP_invoke(");
            emit(iast.getFunction().getMangledName());
            emit(", ");
            emit(arguments.length);
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTvariable tmp = tmps[i];
                emit(", ");
                emit(tmp.getMangledName());
            }
        }
        emit(");\n}\n");
        return null;        
    }
    
    
}
