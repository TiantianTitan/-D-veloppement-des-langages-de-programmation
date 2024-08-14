package com.paracamplus.ilp4.ilp4tme9.compiler;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;
import com.paracamplus.ilp4.compiler.Compiler;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.interfaces.IASTsend;

public class CompilerAvecSiteCache extends Compiler {
    public CompilerAvecSiteCache(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
        super(ioe, igve);
    }

    @Override

    public Void visit(IASTCprogram iast, Context context) throws CompilationException {
        cProgramPrefix = ""
                + "#include <stdio.h> \n"
                + "#include <stdlib.h> \n"
                + "#include \"ilp.h\" \n\n"

                + "#include <time.h> \n";

        cProgramSuffix = "\n"
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
                + "  clock_t start, end; \n"
                + "  start = clock(); \n" //start
                + "  ILP_START_GC; \n"
                + "  ILP_print(ilp_caught_program()); \n"
                + "  ILP_newline(); \n"


                + "  end = clock(); \n" //pver
                + "  fprintf(stderr, \"Temps d'exécution: %f milliseconde\\n \", (float)(end-start)*1000/CLOCKS_PER_SEC ); \n"
                + "  ILP_print_cache_statistics(); \n" // radio


                + "  return EXIT_SUCCESS; \n"
                + "} \n";

            return super.visit(iast, context);
    }

    @Override
    public Void visit(IASTsend iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTvariable tmpMethod = context.newTemporaryVariable();
        emit("  ILP_general_function " + tmpMethod.getMangledName() + "; \n");
        IASTvariable tmpReceiver = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpReceiver.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmpReceiver));

        IASTexpression[] arguments = iast.getArguments();
        IASTvariable[] tmps = new IASTvariable[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("  ILP_Object " + tmp.getMangledName() + "; \n");
            tmps[i] = tmp;
        }

        iast.getReceiver().accept(this, c);
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTexpression expression = arguments[i];
            IASTvariable tmp = tmps[i];
            Context c2 = context.redirect(new AssignDestination(tmp));
            expression.accept(this, c2);
        }

        //global
//        emit(tmpMethod.getMangledName());
//        emit(" = ILP_find_method(");

        //site
        emit("ILP_find_method_site_cache(");
        emit(tmpMethod.getMangledName());
        //emit("= ILP_find_method_site_cache(");
        emit(",");


        emit(tmpReceiver.getMangledName());
        emit(", &ILP_object_");
        emit(Inamed.computeMangledName(iast.getMethodName()));
        emit("_method, ");
        emit(1 + arguments.length);
        emit(");\n");


        emit(context.destination.compile());
        emit(tmpMethod.getName());
        emit("(NULL, ");
        emit(tmpReceiver.getMangledName());
        for ( int i = 0 ; i<arguments.length ; i++ ) {
            emit(", ");
            emit(tmps[i].getMangledName());
        }
        emit(");\n}\n");
        return null;
    }

}
