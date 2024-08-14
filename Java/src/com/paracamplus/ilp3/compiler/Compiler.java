/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCnamedLambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.ReturnDestination;
import com.paracamplus.ilp1.compiler.VoidDestination;
import com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp3.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp3.compiler.normalizer.Normalizer;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class Compiler extends com.paracamplus.ilp2.compiler.Compiler
implements IASTCvisitor<Void, Compiler.Context, CompilationException> {
  
    
    // 
    
	public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
			super(ioe, igve);
		}
	
    
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
        newprogram = (IASTCprogram) optimizer.transform(newprogram);

        GlobalVariableCollector gvc = new GlobalVariableCollector();
        Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
        newprogram.setGlobalVariables(gvs);
        
        FreeVariableCollector fvc = new FreeVariableCollector(newprogram);
        newprogram = fvc.analyze();
      
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
        for ( IASTClambda closure : iast.getClosureDefinitions() ) {
            this.emitPrototype(closure, c);
        }
        emit(cFunctionsPrefix);
        for ( IASTfunctionDefinition ifd : iast.getFunctionDefinitions() ) {
            this.visit(ifd, c);
            emitClosure(ifd, c);
        }
        for ( IASTClambda closure : iast.getClosureDefinitions() ) {
            this.emitFunction(closure, c);
        }
        emit(cFunctionsSuffix);
        
        emit(cBodyPrefix);
        Context cr = context.redirect(ReturnDestination.RETURN_DESTINATION);
        iast.getBody().accept(this, cr);
        emit(cBodySuffix);
        
        emit(cProgramSuffix);
        return null;
    }
 
    
    
    @Override
	public Void visit(IASTClocalFunctionVariable iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("(ILP_Object)&" + globalVariableEnvironment.getCName(iast));
        emit("_closure_object; \n");
        return null;
    }

    
    @Override
	public Void visit(IASTinvocation iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTClocalFunctionInvocation ) {
            return visitGeneralInvocation(iast, context);
        } else if ( iast instanceof IASTCglobalInvocation ) {
            return visit((IASTCglobalInvocation) iast, context);
        } else if ( iast instanceof IASTCcomputedInvocation ) {
            return visit((IASTCcomputedInvocation) iast, context);
        } else {
            return visitGeneralInvocation(iast, context);
        }
    }
   
    
    @Override
	public Void visit(IASTClocalFunctionInvocation iast, Context context)
            throws CompilationException {
        return visitGeneralInvocation(iast, context);
    }
  
    
    @Override
	public Void visit(IASTcodefinitions iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCcodefinitions ) {
            return visit((IASTCcodefinitions)iast, context);
        } else {
            throw new RuntimeException("Should not occur");
        }
    }
    @Override
	public Void visit(IASTCcodefinitions iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTCnamedLambda[] functions = iast.getFunctions();
        for ( IASTCnamedLambda ifd : functions ) {
            emit("  ILP_Object ");
            emit(ifd.getFunctionVariable().getMangledName());
            emit(" = ILP_Value2Box(NULL); \n");
        }
        for ( IASTCnamedLambda fun : functions ) {
            emit("ILP_SetBoxedValue(");
            emit(fun.getFunctionVariable().getMangledName());
            emit(", ILP_make_closure(");
            emit(fun.getName());
            emit(", ");
            emit(fun.getVariables().length);
            emit(", ");
            emit(fun.getClosedVariables().size());
            for ( IASTvariable variable : fun.getClosedVariables() ) {
                emit(", ");
                emit(variable.getMangledName());
            }
            emit("));\n");
        }
        iast.getBody().accept(this, context);
        emit("\n} \n");
        return null;    
    }

    
    protected void emitPrototype(IASTClambda iast, Context context)
            throws CompilationException {
        emit("ILP_Object ");
        emit(iast.getMangledName());
        emit("(ILP_Closure ilp_closure");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit(",\n    ILP_Object ");
            emit(variable.getMangledName());
        }
        emit(");\n");
    }
    
    protected void emitFunction(IASTClambda iast, Context context)
            throws CompilationException {
        emit("ILP_Object ");
        emit(iast.getMangledName());
        emit("(ILP_Closure ilp_closure");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit(",\n    ILP_Object ");
            emit(variable.getMangledName());
        }
        emit(") {\n");
        int i = 0;
        for ( IASTvariable variable : iast.getClosedVariables() ) {
            emit("ILP_Object ");
            emit(variable.getMangledName());
            emit(" = ilp_closure->_content.asClosure.closed_variables[");
            emit(i++);
            emit("]; \n");
        }
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
    }
    
    @Override
	public Void visit(IASTlambda iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTClambda ) {
            return visit((IASTClambda)iast, context);
        } else {
            throw new RuntimeException("Should not occur");
        }
    }
    @Override
	public Void visit(IASTClambda iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("ILP_make_closure(");
        emit(iast.getMangledName());
        emit(", ");
        emit(iast.getVariables().length);
        emit(", ");
        emit(iast.getClosedVariables().size());
        for ( IASTvariable variable : iast.getClosedVariables() ) {
            emit(", ");
            emit(variable.getMangledName());
        }
        emit(");\n");
        return null;
    }
    

    @Override
	public Void visit(IASTtry iast, Context context)
            throws CompilationException {
        emit("{ struct ILP_catcher* current_catcher = ILP_current_catcher; \n");
        emit("  struct ILP_catcher new_catcher;  \n");
        emit("  if ( 0 == setjmp(new_catcher._jmp_buf) ) { \n");
        emit("      ILP_establish_catcher(&new_catcher); \n");
        iast.getBody().accept(this, context);
        emit("      ILP_current_exception = NULL; \n");
        emit("  }; \n");

        if ( iast.getCatcher() != null ) {
            IASTlambda catcher = iast.getCatcher();
            IASTvariable caughtVariable = catcher.getVariables()[0];
            emit("  ILP_reset_catcher(current_catcher); \n");
            emit("  if ( NULL != ILP_current_exception ) { \n");
            emit("      if ( 0 == setjmp(new_catcher._jmp_buf) ) { \n");
            emit("          ILP_establish_catcher(&new_catcher); \n");
            emit("          { ILP_Object ");
            emit(caughtVariable.getMangledName());
            emit(" = ILP_current_exception; \n");
            emit("            ILP_current_exception = NULL; \n");
            Context cc = context.redirect(VoidDestination.VOID_DESTINATION);
            catcher.getBody().accept(this, cc);
            emit("          } \n");
            emit("      }; \n");
            emit("  }; \n");
        }

        emit("  ILP_reset_catcher(current_catcher); \n");
        Context cc = context.redirect(VoidDestination.VOID_DESTINATION);
        if ( iast.getFinallyer() != null ) {
            iast.getFinallyer().accept(this, cc);
        }
        emit("  if ( NULL != ILP_current_exception ) { \n");
        emit("      ILP_throw(ILP_current_exception); \n");
        emit("  }; \n");
        whatever.accept(this, context);
        emit("}\n");
        return null;
    }
    
  
}
