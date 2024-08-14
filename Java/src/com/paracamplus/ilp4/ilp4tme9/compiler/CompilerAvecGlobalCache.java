package com.paracamplus.ilp4.ilp4tme9.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp4.compiler.Compiler;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;

public class CompilerAvecGlobalCache extends Compiler {
    public CompilerAvecGlobalCache(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
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

}
