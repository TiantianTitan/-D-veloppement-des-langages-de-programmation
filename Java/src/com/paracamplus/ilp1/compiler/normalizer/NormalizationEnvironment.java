/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTvariable;

public class NormalizationEnvironment implements INormalizationEnvironment {

    public NormalizationEnvironment (IASTvariable variable,
                                     IASTvariable newvariable,
                                     INormalizationEnvironment next ) {
        this.variable = variable;
        this.newvariable = newvariable;
        this.next = next;
    }
    private final IASTvariable variable;
    private final IASTvariable newvariable;
    private final INormalizationEnvironment next;
    
    @Override
	public INormalizationEnvironment extend (IASTvariable oldv,
                                             IASTvariable newv) {
        return new NormalizationEnvironment(oldv, newv, this);
    }
    
    @Override
	public IASTvariable renaming(IASTvariable otherVariable) 
            throws NoSuchLocalVariableException {
        if ( variable.getName().equals(otherVariable.getName()) ) {
            return newvariable;
        } else {
            return next.renaming(otherVariable);
        }
    }

    public static class EmptyNormalizationEnvironment
    implements INormalizationEnvironment {
        @Override
		public INormalizationEnvironment extend (IASTvariable oldv,
                                                 IASTvariable newv) {
            return new NormalizationEnvironment(oldv, newv, this);
        }

        @Override
		public IASTvariable renaming(IASTvariable otherVariable) 
                throws NoSuchLocalVariableException {
            throw new NoSuchLocalVariableException(otherVariable.getName());
        }
    }
    public static final INormalizationEnvironment EMPTY = 
            new EmptyNormalizationEnvironment();
}
