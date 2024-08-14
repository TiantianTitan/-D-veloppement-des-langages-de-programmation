/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.ast;

import com.paracamplus.ilp1.ast.ASTvariable;

public class ASTCvariable extends ASTvariable {

    public ASTCvariable(String name) {
        super(name);
        this.mutable = false;
    }
    private boolean mutable;

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable() {
        mutable = true;
    }
}
