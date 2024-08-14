/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.ast;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp4.ast.ASTclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;

public class ASTCclassDefinition extends ASTclassDefinition 
implements IASTCclassDefinition {
    public ASTCclassDefinition (String className,
                                IASTCclassDefinition superClass,
                                String[] fieldNames,
                                IASTCmethodDefinition[] methods) {
        super(className, superClass.getName(), fieldNames, methods);
        this.superClass = superClass;
        this.totalFieldNames = computeTotalFieldNames();
    }
    private final IASTCclassDefinition superClass;
    private final String[] totalFieldNames;
    private IASTCmethodDefinition[] totalMethodDefinitions = null;
    
    @Override
	public IASTCclassDefinition getSuperClass () {
        return superClass;
    }
    
    @Override
	public String[] getTotalFieldNames () {
        return totalFieldNames;
    }
    
    public String[] computeTotalFieldNames () {
        List<String> fieldNames = new Vector<>();
        for ( String fieldName : getSuperClass().getTotalFieldNames() ) {
            fieldNames.add(fieldName);
        }
        for ( String fieldName : getProperFieldNames() ) {
            fieldNames.add(fieldName);
        }
        return fieldNames.toArray(new String[0]);
    }
    
    @Override
	public IASTCmethodDefinition[] getProperMethodDefinitions() {
        IASTmethodDefinition[] methods = super.getProperMethodDefinitions();
        IASTCmethodDefinition[] newmethods = 
                new IASTCmethodDefinition[methods.length];
        for ( int i=0 ; i<methods.length ; i++ ) {
            newmethods[i] = (IASTCmethodDefinition) methods[i];
        }
        return newmethods;
    }
    
    @Override
	public IASTCmethodDefinition[] getNewProperMethodDefinitions() {
        IASTCmethodDefinition[] inherited = 
                getSuperClass().getTotalMethodDefinitions();
        List<IASTCmethodDefinition> methods = new Vector<>();
        AddMethod:
        for ( IASTCmethodDefinition proper : getProperMethodDefinitions() ) {
            for ( IASTCmethodDefinition md : inherited ) {
                if ( md.getMethodName().equals(proper.getMethodName()) ) {
                    continue AddMethod;
                }
            }
            methods.add(proper);
        }
        return methods.toArray(new IASTCmethodDefinition[0]);
    }
    
    @Override
	public IASTCmethodDefinition[] getTotalMethodDefinitions() {
        // Defer initialization (see IASTCclassDefinition.visit)
        if ( totalMethodDefinitions == null ) {
            totalMethodDefinitions = computeTotalMethodDefinitions();
        }
        return totalMethodDefinitions;
    }
    
    public IASTCmethodDefinition[] computeTotalMethodDefinitions() {
        Vector<IASTCmethodDefinition> keptMethods = new Vector<>();
        for ( IASTCmethodDefinition inherited : 
                    getSuperClass().getTotalMethodDefinitions() ) {
            keptMethods.add(inherited);
        }
        AddMethod:
        for ( IASTCmethodDefinition proper : getProperMethodDefinitions() ) {
            String methodName = proper.getMethodName();
            for ( int i=0 ; i<keptMethods.size() ; i++ ) {
                if ( methodName.equals(keptMethods.get(i).getMethodName()) ) {
                    keptMethods.set(i, proper);
                    continue AddMethod;
                }
            }
            keptMethods.add(proper);
        }
        return keptMethods.toArray(new IASTCmethodDefinition[0]);
    }
}
