/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.ast.ASTnamed;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;

public class ASTclassDefinition extends ASTnamed
implements IASTclassDefinition {
    
    public ASTclassDefinition (String className,
                               String superClassName,
                               String[] fieldNames,
                               IASTmethodDefinition[] methods) {
        super(className);
        this.superClassName = superClassName;
        this.fieldNames = fieldNames;
        this.methods = methods;
        this.methodNames = new String[methods.length];
    }
    private final String superClassName;
    private final String[] fieldNames;
    private final IASTmethodDefinition[] methods;
    private String[] methodNames = null;

    @Override
	public String getSuperClassName() {
        return superClassName;
    }

    @Override
	public String[] getProperFieldNames() {
        return fieldNames;
    }

    public int getProperFieldCount() {
        return fieldNames.length;
    }

    @Override
	public String[] getProperMethodNames() {
        // Defer initialization (see IASTCclassDefinition.visit)
        if ( methodNames == null ) {
            for ( int i=0 ; i<methods.length ; i++ ) {
                IASTmethodDefinition method = this.methods[i];
                this.methodNames[i] = method.getFunctionVariable().getName();
            }
        }
        return methodNames;
    }

    public int getProperMethodCount() {
        return getProperMethodNames().length;
    }

    @Override
	public IASTmethodDefinition[] getProperMethodDefinitions() {
        return methods;
    }
}
