/* *****************************************************************
 * ilp1 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp1
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.paracamplus.ilp1.ast.ASTboolean;
import com.paracamplus.ilp1.ast.ASTvariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.compiler.interfaces.IDestination;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOptimizer;
import com.paracamplus.ilp1.compiler.interfaces.IPrimitive;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp1.compiler.normalizer.Normalizer;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class Compiler 
implements IASTCvisitor<Void, Compiler.Context, CompilationException> {
    
    public static class Context {
        public Context (IDestination destination) {
            this.destination = destination;
        }
        public IDestination destination;
        public static AtomicInteger counter = new AtomicInteger(0);
        
        public IASTvariable newTemporaryVariable () {
            int i = counter.incrementAndGet();
            return new ASTvariable("ilptmp" + i);
        }
        
        public Context redirect (IDestination d) {
            if ( d == destination ) {
                return this;
            } else {
                return new Context(d);
            }
        }
    }
    
    // 
    
    public Compiler (IOperatorEnvironment ioe,
                     IGlobalVariableEnvironment igve ) {
        this.operatorEnvironment = ioe;
        this.globalVariableEnvironment = igve;
    }
    protected final IOperatorEnvironment operatorEnvironment;
    protected final IGlobalVariableEnvironment globalVariableEnvironment;
    
    public void setOptimizer (IOptimizer optimizer) {
        this.optimizer = optimizer;
    }
    protected IOptimizer optimizer;
    
    //

    public void emit (String s) throws CompilationException {
        try {
            out.append(s);
        } catch (IOException e) {
            throw new CompilationException(e);
        }
    }
    public void emit (char c) throws CompilationException {
        try {
            out.append(c);
        } catch (IOException e) {
            throw new CompilationException(e);
        }
    }
    public void emit (int i) throws CompilationException {
        try {
            out.append(Integer.toString(i));
        } catch (IOException e) {
            throw new CompilationException(e);
        }
    }
    
    //
    
    public IASTCprogram normalize(IASTprogram program) 
            throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }
   
    public String compile(IASTprogram program) 
            throws CompilationException {
        
        IASTCprogram newprogram = normalize(program);
        newprogram = optimizer.transform(newprogram);

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
    protected Writer out;

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
        
        emit(cBodyPrefix);
        Context cr = context.redirect(ReturnDestination.RETURN_DESTINATION);
        iast.getBody().accept(this, cr);
        emit(cBodySuffix);
        
        emit(cProgramSuffix);
        return null;
    }
    protected String cProgramPrefix = ""
            + "#include <stdio.h> \n"
            + "#include <stdlib.h> \n"
            + "#include \"ilp.h\" \n\n";
    protected String cGlobalVariablesPrefix = ""
            + "/* Global variables */ \n";
    protected String cGlobalVariablesSuffix = ""
            + "\n";

    protected String cBodyPrefix = "\n"
            + "ILP_Object ilp_program () \n"
            + "{ \n";
    protected String cBodySuffix = "\n"
            + "} \n";
    protected String cProgramSuffix = "\n"
            + "static ILP_Object ilp_caught_program () {\n"
            + "  struct ILP_catcher* current_catcher = ILP_current_catcher;\n"
            + "  struct ILP_catcher new_catcher;\n\n"
            + "  if ( 0 == setjmp(new_catcher._jmp_buf) ) {\n"
            + "    ILP_establish_catcher(&new_catcher);\n"
            + "    return ilp_program();\n"
            + "  };\n"
            + "  return ILP_current_exception;\n"
            + "}\n\n"
            + "int main (int argc, char *argv[]) \n"
            + "{ \n"
            + "  ILP_START_GC; \n"
            + "  ILP_print(ilp_caught_program()); \n"
            + "  ILP_newline(); \n"
            + "  return EXIT_SUCCESS; \n"
            + "} \n";    
    
    @Override
	public Void visit(IASTsequence iast, Context context)
            throws CompilationException {
        IASTvariable tmp = context.newTemporaryVariable();
        IASTexpression[] expressions = iast.getExpressions();
        Context c = context.redirect(new AssignDestination(tmp));
        emit("{ \n");
        emit("  ILP_Object " + tmp.getMangledName() + "; \n");
        for ( IASTexpression expr : expressions ) {
            expr.accept(this, c);
        }
        emit(context.destination.compile());
        emit(tmp.getMangledName());
        emit("; \n} \n");
        return null;
    }

    @Override
	public Void visit(IASTvariable iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTClocalVariable ) {
            return visit((IASTClocalVariable) iast, context);
        } else {
            return visit((IASTCglobalVariable) iast, context);
        }
    }
    @Override
	public Void visit(IASTCvariable iast, Context context)
            throws CompilationException {
        throw new RuntimeException("should not occur");
    }
    @Override
	public Void visit(IASTClocalVariable iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        if ( iast.isClosed() ) {
            emit("ILP_Box2Value(");
            emit(iast.getMangledName());
            emit(")");
        } else {
            emit(iast.getMangledName());
        }
        emit("; \n");
        return null;
    }

    @Override
	public Void visit(IASTCglobalVariable iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit(globalVariableEnvironment.getCName(iast));
        emit("; \n");
        return null;
    }


    @Override
	public Void visit(IASTboolean iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        if ( iast.getValue() ) {
            emit("ILP_TRUE");
        } else {
            emit("ILP_FALSE");
        }
        emit("; \n");
        return null;
    }

    @Override
	public Void visit(IASTinteger iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("ILP_Integer2ILP(");
        emit(iast.getValue().toString());
        emit("); \n");
        return null;
    }
    
    @Override
	public Void visit(IASTfloat iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("ILP_Float2ILP(");
        emit(iast.getValue().toString());
        emit("); \n");
        return null;
    }

    @Override
	public Void visit(IASTstring iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit(" ILP_String2ILP(\"");
        final String s = iast.getValue();
        for ( int i=0 ; i<s.length() ; i++ ) {
          char c = s.charAt(i);
          switch ( c ) {
          case '\\':
          case '"': {
            emit("\\");
          }
        //$FALL-THROUGH$
        default: {
            emit(c);
          }
          }
        }
        emit("\"); \n");
        return null;
    }

    @Override
	public Void visit(IASTunaryOperation iast, Context context)
            throws CompilationException {
        IASTvariable tmp1 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        Context c1 = context.redirect(new AssignDestination(tmp1));
        iast.getOperand().accept(this, c1);
        String cName = operatorEnvironment.getUnaryOperator(iast.getOperator());
        emit(context.destination.compile());
        emit(cName);
        emit("(");
        emit(tmp1.getMangledName());
        emit(");\n");
        emit("} \n");
        return null;
    }

    @Override
	public Void visit(IASTbinaryOperation iast, Context context)
            throws CompilationException {
        IASTvariable tmp1 = context.newTemporaryVariable();
        IASTvariable tmp2 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        emit("  ILP_Object " + tmp2.getMangledName() + "; \n");
        Context c1 = context.redirect(new AssignDestination(tmp1));
        iast.getLeftOperand().accept(this, c1);
        Context c2 = context.redirect(new AssignDestination(tmp2));
        iast.getRightOperand().accept(this, c2);
        String cName = operatorEnvironment.getBinaryOperator(iast.getOperator());
        emit(context.destination.compile());
        emit(cName);
        emit("(");
        emit(tmp1.getMangledName());
        emit(", ");
        emit(tmp2.getMangledName());
        emit(");\n");
        emit("} \n");
        return null;
    }
    
    @Override
	public Void visit(IASTalternative iast, Context context)
            throws CompilationException {
        IASTvariable tmp1 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmp1));
        iast.getCondition().accept(this, c);
        emit("  if ( ILP_isEquivalentToTrue(");
        emit(tmp1.getMangledName());
        emit(" ) ) {\n");
        iast.getConsequence().accept(this, context);
        if ( iast.isTernary() ) {
            emit("\n  } else {\n");
            iast.getAlternant().accept(this, context);
        }
        emit("\n  }\n}\n");
        return null;
    }
    

    @Override
	public Void visit(IASTblock iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTbinding[] bindings = iast.getBindings();
        IASTvariable[] tmps = new IASTvariable[bindings.length];
        for ( int i=0 ; i<bindings.length ; i++ ) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("  ILP_Object " + tmp.getMangledName() + "; \n");
            tmps[i] = tmp;
        }
        for ( int i=0 ; i<bindings.length ; i++ ) {
            IASTbinding binding = bindings[i];
            IASTvariable tmp = tmps[i];
            Context c = context.redirect(new AssignDestination(tmp));
            binding.getInitialisation().accept(this, c);
        }
        emit("\n  {\n");
        for ( int i=0 ; i<bindings.length ; i++ ) {
            IASTbinding binding = bindings[i];
            IASTvariable tmp = tmps[i];
            IASTvariable variable = binding.getVariable();
            emit("    ILP_Object ");
            emit(variable.getMangledName());
            emit(" = ");
            if ( variable instanceof IASTClocalVariable ) {
                IASTClocalVariable lv = (IASTClocalVariable) variable;
                if ( lv.isClosed() ) {
                    emit("ILP_Value2Box(");
                    emit(tmp.getMangledName());
                    emit(")");
                } else {
                    emit(tmp.getMangledName());
                }
            } else {
                emit(tmp.getMangledName());
            }
            emit(";\n");
        }
        iast.getBody().accept(this, context);
        emit("\n  }\n}\n");
        return null;
    }
        
    @Override
	public Void visit(IASTinvocation iast, Context context)
            throws CompilationException {
    	System.out.println (iast + " " + ( iast instanceof IASTCglobalInvocation ));
        if ( iast instanceof IASTCglobalInvocation ) {
            return visit((IASTCglobalInvocation) iast, context);
        } else if ( iast instanceof IASTCcomputedInvocation ) {
            return visit((IASTCcomputedInvocation) iast, context);
        } else {
            return visitGeneralInvocation(iast, context);
        }
    }
    
    @Override
	public Void visit(IASTCcomputedInvocation iast, Context context)
            throws CompilationException {
            return visitGeneralInvocation(iast, context);
        }
   
    
    protected Void visitGeneralInvocation(IASTinvocation iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTexpression fexpr = iast.getFunction();
        IASTvariable tmpf = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpf.getMangledName() + "; \n");
        IASTexpression[] arguments = iast.getArguments();
        IASTvariable[] tmps = new IASTvariable[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("  ILP_Object " + tmp.getMangledName() + "; \n");
            tmps[i] = tmp;
        }
        Context cf = context.redirect(new AssignDestination(tmpf));
        fexpr.accept(this, cf);
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTexpression expression = arguments[i];
            IASTvariable tmp = tmps[i];
            Context c = context.redirect(new AssignDestination(tmp));
            expression.accept(this, c);
        }
        emit(context.destination.compile());
        emit("ILP_invoke(");
        emit(tmpf.getMangledName());
        emit(", ");
        emit(arguments.length);
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTvariable tmp = tmps[i];
            emit(", ");
            emit(tmp.getMangledName());
        }
        emit(");\n}\n");
        return null;
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
        } else { 
        	throw new CompilationException("not a primitive");
        }
        emit(");\n}\n");
        return null;        
    }
    
    
    protected IASTboolean whatever =
            new ASTboolean("false");

  
}
