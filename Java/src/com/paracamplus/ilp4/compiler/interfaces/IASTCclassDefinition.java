/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.interfaces;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp1.interfaces.Inamed;

public interface IASTCclassDefinition extends IASTclassDefinition, Inamed {
    @Override
	IASTCmethodDefinition[] getProperMethodDefinitions();
    
    IASTCmethodDefinition[] getNewProperMethodDefinitions();
    
    IASTCmethodDefinition[] getTotalMethodDefinitions();
    
    default int getTotalMethodDefinitionsCount() {
        return getTotalMethodDefinitions().length;
    }
    
    default int getMethodOffset (IASTCmethodDefinition method)
            throws CompilationException {
        IASTCmethodDefinition[] methods = getTotalMethodDefinitions();
        for ( int i=0 ; i<methods.length ; i++ ) {
            if ( method.getMethodName().equals(methods[i].getMethodName()) ) {
                return i;
            }
        }
        String msg = "No such method " + method.getName();
        throw new CompilationException(msg);
    }
    
    IASTCclassDefinition getSuperClass();
    
    @Override
	default String getSuperClassName() {
        return getSuperClass().getName();
    }
    
    String[] getTotalFieldNames();
    
    default int getFieldOffset(String fieldName) throws CompilationException {
        String[] fieldNames = getTotalFieldNames();
        for ( int i=0 ; i< fieldNames.length ; i++ ) {
           if ( fieldNames[i].equals(fieldName) ) {
               return i;
           }
       }
        String msg = "No such field " + fieldName;
        throw new CompilationException(msg);
    }
}
