/* *****************************************************************
 * ilp4 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp4
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.ReturnDestination;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldWrite;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCinstantiation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp4.compiler.normalizer.Normalizer;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;


public class Compiler extends com.paracamplus.ilp3.compiler.Compiler
implements IASTCvisitor<Void, Compiler.Context, CompilationException> {
  
    
	public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
			super(ioe, igve);
		}

 
    
    public IASTCprogram normalize(IASTprogram program, 
                                  IASTCclassDefinition objectClass) 
            throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf, objectClass);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }
   
    public String compile(IASTprogram program, 
                          IASTCclassDefinition objectClass) 
            throws CompilationException {
        
        IASTCprogram newprogram = normalize(program, objectClass);
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
        
        emit(cClassPrefix);
        for ( IASTCclassDefinition cd : iast.getClassDefinitions() ) {
            emitClassHeader(cd);
            visit(cd, c);
        }
        for ( IASTCclassDefinition cd : iast.getClassDefinitions() ) {
            for ( IASTCmethodDefinition md : cd.getProperMethodDefinitions() ) {
                visit(md, context);
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
     
    protected String cClassPrefix = "\n"
            + "/* Classes */ \n";
    protected String cClassSuffix = "\n";

   

   
    
    // Class related

    public Void visit(IASTCclassDefinition iast, Context context)
            throws CompilationException {
        String lastFieldName = "NULL";
        int inheritedFieldsCount = 0;
        if ( ! "Object".equals(iast.getSuperClassName()) ) {
            IASTCclassDefinition superClass = iast.getSuperClass();
            String[] fieldNames = superClass.getTotalFieldNames();
            inheritedFieldsCount = fieldNames.length;
            if ( inheritedFieldsCount > 0 ) {
                String fieldName = fieldNames[inheritedFieldsCount - 1];
                String mangledFieldName = Inamed.computeMangledName(fieldName);
                lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
            }
        }
        String[] fieldNames = iast.getProperFieldNames();
        for ( int i=0 ; i<fieldNames.length ; i++ ) {
            String mangledFieldName = Inamed.computeMangledName(fieldNames[i]);
            emit("\nstruct ILP_Field ILP_object_");
            emit(mangledFieldName);
            emit("_field = {\n  &ILP_object_Field_class,\n     { { ");
            emit("(ILP_Class) &ILP_object_");
            emit(iast.getMangledName());
            emit("_class,\n   ");
            emit(lastFieldName);
            emit(",\n    \"");
            emit(mangledFieldName);
            emit("\",\n  ");
            emit(i + inheritedFieldsCount);
            emit(" } }\n};\n");
            lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
        }

        emit("\nstruct ILP_Class");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(" ILP_object_");
        emit(iast.getMangledName());
        emit("_class = {\n  &ILP_object_Class_class,\n  { { ");
        emit("(ILP_Class) &ILP_object_");
        emit(iast.getSuperClass().getMangledName());
        emit("_class,\n         \"");
        emit(iast.getMangledName());
        emit("\",\n         ");
        emit(inheritedFieldsCount + fieldNames.length);
        emit(",\n         ");
        emit(lastFieldName);
        emit(",\n         ");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(",\n { ");
        for ( IASTCmethodDefinition md : iast.getTotalMethodDefinitions() ) {
            emit(md.getCName());
            emit(", \n");
        }
        emit(" } } }\n};\n");
        
        IASTCmethodDefinition[] methods = iast.getNewProperMethodDefinitions();
        for ( int i = 0 ; i<methods.length ; i++ ) {
            IASTCmethodDefinition method = methods[i];
            if ( ! alreadyGeneratedMethodObject.containsKey(method.getName()) ) {
                emit("\nstruct ILP_Method ILP_object_");
                emit(Inamed.computeMangledName(method.getMethodName()));
                emit("_method = {\n  &ILP_object_Method_class,\n  { { ");
                emit("(struct ILP_Class*) &ILP_object_");
                emit(iast.getMangledName());
                emit("_class,\n  \"");
                emit(method.getMethodName());
                emit("\",\n  ");
                emit(method.getVariables().length);
                emit(",  /* arité */\n  ");
                emit(iast.getMethodOffset(method));
                emit(" /* offset */ \n    } }\n};\n");
            }
        }
        return null;
    }
    protected Map<String, Boolean> alreadyGeneratedMethodObject = new HashMap<>();  
        
    public void emitClassHeader(IASTCclassDefinition iast)
            throws CompilationException {
        emitClassMacro(iast);
        int numberMethods = iast.getTotalMethodDefinitionsCount();
        emit("extern struct ILP_Class");
        emit(numberMethods);
        emit(" ILP_object_");
        emit(iast.getMangledName());
        emit("_class; \n");
        for ( String fieldName : iast.getProperFieldNames() ) {
            emit("extern struct ILP_Field ILP_object_");
            emit(Inamed.computeMangledName(fieldName)); 
            emit("_field; \n");
        }
        for ( IASTCmethodDefinition md : iast.getProperMethodDefinitions() ) {
            emitPrototype(md);
        }
    }
    protected void emitClassMacro(IASTCclassDefinition iast)
            throws CompilationException {
        int numberMethods = iast.getTotalMethodDefinitionsCount();
        numberMethods = (numberMethods==0) ? 1 : numberMethods;
        if ( ! alreadyDoneClassPrototypes.containsKey(numberMethods) ) {
            emit("ILP_GenerateClass(");
            emit(numberMethods);
            emit(");\n");
            alreadyDoneClassPrototypes.put(numberMethods, true);
        }
    }
    protected Map<Integer,Boolean> alreadyDoneClassPrototypes = new HashMap<>();

    public Void visit(IASTCmethodDefinition iast, Context context)
            throws CompilationException {
        emit("\nILP_Object ");
        emit(iast.getCName());
        emit("(ILP_Closure ilp_useless,\n");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit("    ILP_Object ");
            emit(variable.getMangledName());
            if ( i < variables.length-1 ) {
                emit(",\n");
            }
        }
        emit(") {\n");
        
        IASTCmethodDefinition superMethod = iast.findSuperMethod();
        emit("static ILP_Method ilp_CurrentMethod = &ILP_object_");
        emit(Inamed.computeMangledName(iast.getMethodName()));
        emit("_method;\n");
        emit("static ILP_general_function ilp_SuperMethod = ");
        if ( superMethod != null ){
            emit(superMethod.getCName());
        } else {
            emit("NULL");
        }
        emit(";\n");
        
        emit("ILP_Object ilp_CurrentArguments[");
        emit(iast.getVariables().length);
        emit("];\n");
        for ( int i=0 ; i<iast.getVariables().length ; i++ ) {
            emit(" ilp_CurrentArguments[");
            emit(i);
            emit("] = ");
            emit(iast.getVariables()[i].getMangledName());
            emit(";\n");
        }

        emit("\n{\n");
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
        emit("}\n}\n");
        return null;
    }
    
    private void emitPrototype(IASTCmethodDefinition iast)
            throws CompilationException {
        emit("ILP_Object ilp__");
        emit(iast.getFunctionVariable().getMangledName()); 
        emit("(ILP_Closure ilp_useless, ");
        IASTvariable[] variables = iast.getVariables();
        for ( int i=0 ; i< variables.length ; i++ ) {
            IASTvariable variable = variables[i];
            emit("    ILP_Object ");
            emit(variable.getMangledName());
            if ( i < variables.length-1 ) {
                emit(",\n");
            }
        }
        emit(");\n");
    }
    
    @Override
	public Void visit(IASTinstantiation iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTvariable tmpInstance = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        
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
        
        emit(tmpInstance.getMangledName());
        emit(" = ILP_MakeInstance(");
        emit(Inamed.computeMangledName(iast.getClassName()));
        emit("); \n");
        
        for ( int i=0 ; i<arguments.length ; i++ ) {
            emit(tmpInstance.getMangledName());
            emit("->_content.asInstance.field[");
            emit(i);
            emit("] = ");
            emit(tmps[i].getMangledName());
            emit("; \n");
        }
        
        emit(context.destination.compile());
        emit(tmpInstance.getMangledName());
        emit("; \n}\n");
        return null;
    }
    
    public Void visit(IASTCinstantiation iast, Context context)
            throws CompilationException {
        return visit((IASTinstantiation)iast, context);
    }
    
    @Override
	public Void visit(IASTfieldRead iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCfieldRead ) {
            return visit((IASTCfieldRead)iast, context);
        } else {
            String msg = "Should not occur";
            throw new CompilationException(msg);
        }
    }
 
    @Override
	public Void visit(IASTCfieldRead iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTvariable tmpInstance = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmpInstance));
        iast.getTarget().accept(this, c);
        
        IASTCclassDefinition clazz = iast.getDefiningClass();
        emit("if ( ILP_IsA(");
        emit(tmpInstance.getMangledName());
        emit(", ");
        emit(clazz.getMangledName());
        emit(" ) ) {\n");
        emit(context.destination.compile());
        emit(tmpInstance.getMangledName());
        emit("->_content.asInstance.field[");
        emit(clazz.getFieldOffset(iast.getFieldName()));
        emit("];\n} else {\n");
        emit(context.destination.compile());
        emit(" ILP_UnknownFieldError(\"");
        emit(Inamed.computeMangledName(iast.getFieldName()));
        emit("\", ");
        emit(tmpInstance.getMangledName());
        emit(");\n}\n}\n");
        return null;
    }
    
    @Override
	public Void visit(IASTfieldWrite iast, Context context)
            throws CompilationException {
        if ( iast instanceof IASTCfieldWrite ) {
            return visit((IASTCfieldWrite)iast, context);
        } else {
            String msg = "Should not occur";
            throw new CompilationException(msg);
        }
    }
    
    @Override
	public Void visit(IASTCfieldWrite iast, Context context)
            throws CompilationException {
        emit("{ \n");
        IASTvariable tmpInstance = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmpInstance));

        IASTvariable tmpValue = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpValue.getMangledName() + "; \n");
        Context cv = context.redirect(new AssignDestination(tmpValue));
        
        iast.getTarget().accept(this, c);
        iast.getValue().accept(this, cv);
        
        IASTCclassDefinition clazz = iast.getDefiningClass();
        emit("if ( ILP_IsA(");
        emit(tmpInstance.getMangledName());
        emit(", ");
        emit(clazz.getMangledName());
        emit(" ) ) {\n");
        emit(context.destination.compile());
        emit(tmpInstance.getMangledName());
        emit("->_content.asInstance.field[");
        emit(clazz.getFieldOffset(iast.getFieldName()));
        emit("] = ");
        emit(tmpValue.getMangledName());
        emit(";\n} else {\n");
        emit(context.destination.compile());
        emit(" ILP_UnknownFieldError(\"");
        emit(Inamed.computeMangledName(iast.getFieldName()));
        emit("\", ");
        emit(tmpInstance.getMangledName());
        emit(");\n}\n}\n");
        return null;
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

        emit(tmpMethod.getMangledName());
        emit(" = ILP_find_method(");
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
    
    @Override
	public Void visit(IASTself iast, Context context)
            throws CompilationException {
        // Totally removed now, see Normalizer.visit(IASTself,...)
        throw new RuntimeException("NYI");
    }
        
    @Override
	public Void visit(IASTsuper iast, Context context)
            throws CompilationException {
        emit(context.destination.compile());
        emit("ILP_FindAndCallSuperMethod(); \n");
        return null;
    }
}
