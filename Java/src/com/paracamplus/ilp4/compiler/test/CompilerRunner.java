package com.paracamplus.ilp4.compiler.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.tools.FileTool;
import com.paracamplus.ilp1.tools.ProgramCaller;
import com.paracamplus.ilp4.compiler.Compiler;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class CompilerRunner 
extends com.paracamplus.ilp1.compiler.test.CompilerRunner {

    private Compiler compiler;
    public void setCompiler(Compiler compiler)  {
	   this.compiler = compiler;
   }

    @Override
    public String compileAndRun(File file) 
    		throws ParseException, CompilationException, IOException {
        System.err.println("Testing " + file.getAbsolutePath() + " ...");
        assertTrue(file.exists());

        // lancement du parsing        
        IASTprogram program = (IASTprogram) parser.parse(file);
        
        // lancement de la compilation vers C
        if (compiler == null) {
        	throw new CompilationException("compiler not set");
        }
        IASTCclassDefinition objectClass = createObjectClass();
        String compiled = compiler.compile(program, objectClass);
        File cFile = FileTool.changeSuffix(file, "c");
        FileTool.stuffFile(cFile, compiled);
        
        // detection de Windows
        boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0;

        String cPath =  cFile.getAbsolutePath();
        if (isWindows) {
        	// sous Windows, adaptation du chemin du fichier
        	cPath = "\"/mnt/" + cPath.substring(0,1).toLowerCase() + cPath.replace('\\', '/').substring(2) + "\"";
        }

        // pretty-print du C généré et affichage
        try {
            String indentProgram = "indent " + cPath;
            ProgramCaller pcindent = new ProgramCaller(indentProgram);
            pcindent.run();
        } catch (Exception exc) {
        }
        System.out.println(FileTool.slurpFile(cFile));

        // lancement du script de compilation et d'exécution
        if (runtimeScript == null) {
        	throw new CompilationException("runtime script not set");
        }
        String compileProgram = "bash " + runtimeScript + " " + cPath;
        ProgramCaller pc = new ProgramCaller(compileProgram);
        pc.setVerbose();
        pc.run();
        assertEquals("Comparing return code", 0, pc.getExitValue());
        return pc.getStdout().trim();	
    }

    
    protected IASTCclassDefinition createObjectClass() {
	        IASTCclassDefinition oc = new IASTCclassDefinition() {
	            @Override
				public String getName() {
	                return "Object";
	            }
	            @Override
				public IASTCclassDefinition getSuperClass() {
	                throw new RuntimeException("No super class for Object");
	            }
	            @Override
				public String[] getProperFieldNames() {
	                return new String[0];
	            }
	            @Override
				public String[] getTotalFieldNames() {
	                return getProperFieldNames();
	            }
	            @Override
				public int getFieldOffset(String fieldName)
	                    throws CompilationException {
	                String msg = "No such field " + fieldName;
	                throw new CompilationException(msg);
	            }
	            @Override
				public IASTCmethodDefinition[] getProperMethodDefinitions() {
	                return predefinedMethods;
	            }
	            @Override
				public IASTCmethodDefinition[] getNewProperMethodDefinitions() {
	                return predefinedMethods;
	            }
	            protected IASTCmethodDefinition[] predefinedMethods =
	                    new IASTCmethodDefinition[2];
	            @Override
				public IASTCmethodDefinition[] getTotalMethodDefinitions() {
	                return getProperMethodDefinitions();
	            }
	        };
	        IASTCmethodDefinition mdprint = new ASTCprimitiveMethodDefinition(
	                "print", "ILPm_print", oc);
	        oc.getProperMethodDefinitions()[0] = mdprint;
	        IASTCmethodDefinition mdclassOf = new ASTCprimitiveMethodDefinition(
	                "classOf", "ILPm_classOf", oc);
	        oc.getProperMethodDefinitions()[1] = mdclassOf;
	        return oc;
	    }
	    
	    public static class ASTCprimitiveMethodDefinition 
	    implements IASTCmethodDefinition {
	        public ASTCprimitiveMethodDefinition(
	                String methodName,
	                String cName,
	                IASTCclassDefinition clazz ) {
	            this.methodName = methodName;
	            this.cName = cName;
	            this.clazz = clazz;
	        }
	        private final String methodName;
	        private final String cName;
	        private final IASTCclassDefinition clazz;

	        @Override
			public String getMethodName() {
	            return methodName;
	        }
	        @Override
			public String getName() {
	            return cName;
	        }
	        @Override
			public String getCName() {
	            return cName;
	        }
	        // FIXME Missing getMangledName() ???
	        @Override
			public IASTCclassDefinition getDefiningClass() {
	            return clazz;
	        }
	        @Override
			public String getDefiningClassName() {
	            return clazz.getName();
	        }
	        @Override
			public IASTvariable getFunctionVariable() {
	            return methodVariable;
	        }
	        IASTvariable methodVariable = new IASTvariable() {
	            @Override
				public String getName() {
	                return cName;
	            }
	            @Override
				public <Result, Data, Anomaly extends Throwable> Result accept(
						com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
						Data data) throws Anomaly {
					return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
				}
	        };
	        @Override
			public IASTvariable[] getVariables() {
	            throw new RuntimeException("NYI"); // FIXME
	        }
	        @Override
			public IASTexpression getBody() {
	            throw new RuntimeException("NYI"); // FIXME
	        }
	        @Override
			public Set<IASTvariable> getClosedVariables() {
	            return closedVariables;
	        }
	        @Override
			public void setClosedVariables(Set<IASTClocalVariable> newvars) {
	            this.closedVariables.addAll(closedVariables);
	        }
	        private final Set<IASTvariable> closedVariables =
	                new HashSet<>();
	        @Override
			public IASTCmethodDefinition findSuperMethod() 
	                throws CompilationException {
	            String msg = "No super method";
	            throw new CompilationException(msg);
	        }
	    }
	    
	
}
