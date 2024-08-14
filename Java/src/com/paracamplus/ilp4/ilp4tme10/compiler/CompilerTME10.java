package com.paracamplus.ilp4.ilp4tme10.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.ReturnDestination;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
//import com.paracamplus.ilp4.ilp4tme10.compiler.interfaces.IASTCvisitorTME10;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
//import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.INormalizationFactory;
//import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.NormalizationFactory;
//import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.Normalizer;
import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.INormalizationFactoryTME10;
import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.NormalizationFactoryTME10;
import com.paracamplus.ilp4.ilp4tme10.compiler.normalizer.NormalizerTME10;
import com.paracamplus.ilp4.ilp4tme10.compiler.interfaces.IASTCvisitorTME10;
public class CompilerTME10
	extends com.paracamplus.ilp4.compiler.Compiler
    implements IASTCvisitorTME10<Void, CompilerTME10.Context, CompilationException> {

	public CompilerTME10(IOperatorEnvironment ioe,
                         IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}
	
	
	// Normalisation étendue
	@Override
    public IASTCprogram normalize(IASTprogram program, 
            IASTCclassDefinition objectClass) 
            		throws CompilationException {
    	INormalizationFactoryTME10 nf = new NormalizationFactoryTME10();
    	NormalizerTME10 normalizer = new NormalizerTME10(nf, objectClass);
    	IASTCprogram newprogram = normalizer.transform(program);
    	return newprogram;
    }


    // Branchement sur les collecteurs de variables étendus
	@Override
    public String compile(IASTprogram program, 
    		IASTCclassDefinition objectClass) 
    				throws CompilationException {

    	IASTCprogram newprogram = normalize(program, objectClass);
    	newprogram = (IASTCprogram) optimizer.transform(newprogram);

    	GlobalVariableCollectorTME10 gvc = new GlobalVariableCollectorTME10();
    	Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
    	newprogram.setGlobalVariables(gvs);
    	allGlobals = gvs;
    	
    	FreeVariableCollectorTME10 fvc = new FreeVariableCollectorTME10(newprogram);
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

    // Copie locale de l'ensemble des globales, pour simplifier
    protected Set<IASTCglobalVariable> allGlobals;
    
    
    /*
     * Visiteurs pour exists et defined
     */
    
	@Override
	public Void visit(IASTexists iast, Context context)
			throws CompilationException {
		/*
		 * A ce point, on sait exactement quelles variables existent.
		 * On peut donc compiler exists en une constante booléenne !
		 */
		IASTvariable var = iast.getVariable();
		boolean present = 
				(var instanceof IASTClocalVariable) ||
				(var instanceof IASTCglobalFunctionVariable) ||
				allGlobals.contains(var) || 
				((IGlobalVariableEnvironment)globalVariableEnvironment).contains(var);

		// Génération de code
        emit(context.destination.compile());
        emit(present ? "ILP_TRUE" : "ILP_FALSE");
        emit("; \n");
        return null;
	}


	@Override
	public Void visit(IASTdefined iast, Context context)
			throws CompilationException {

		/*
		 * Le compilateur a été modifié pour initialiser à NULL toutes les
		 * variables globales. Cela permet de tester de manière fiable si
		 * elles ont été initialisées.
		 */
		
		// Génération de code
        IASTvariable tmp1 = context.newTemporaryVariable();
        emit("{ \n");
        emit("  ILP_Object " + tmp1.getMangledName() + "; \n");
        Context c1 = context.redirect(new AssignDestination(tmp1));
        iast.getVariable().accept(this, c1);
        emit(context.destination.compile());
        emit("(");
        emit(tmp1.getMangledName());
        emit(" != NULL ? ILP_TRUE : ILP_FALSE");
        emit("); \n");
        emit("} \n");
		return null;
	}


	/*
	 * Copié depuis Compiler.
	 * 
	 * Seul changement : initialisation des globales à NULL.
	 * Note : on ne peut pas supposer que le compilateur C initialise 
	 * les globales à NULL ; il faut le faire soi-même.
	 */
	@Override
    public Void visit(IASTCprogram iast, Context context)
            throws CompilationException {
        emit(cProgramPrefix);
        
        emit(cGlobalVariablesPrefix);
        for ( IASTCglobalVariable gv : iast.getGlobalVariables() ) {
            emit("ILP_Object ");
            emit(gv.getMangledName());
            emit(" = NULL;\n"); // Modification TME10
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
        
        emit(cClassPrefix);
        for ( IASTCclassDefinition cd : iast.getClassDefinitions() ) {
            emitClassHeader(cd);
            visit(cd, c);
        }
        for ( IASTCclassDefinition cd : iast.getClassDefinitions() ) {
            for ( IASTCmethodDefinition md : cd.getProperMethodDefinitions() ) {
                visit((IASTCmethodDefinition)md, context);
            }
        }        
        emit(cClassSuffix);
        
        emit(cBodyPrefix);
        Context cr = context.redirect(ReturnDestination.RETURN_DESTINATION);
        iast.getBody().accept(this, cr);
        emit(cBodySuffix);
        
        emit(cProgramSuffix);
        return null;
    }
 		
}
