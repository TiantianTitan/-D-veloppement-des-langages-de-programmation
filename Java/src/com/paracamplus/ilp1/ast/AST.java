/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IAST;

public abstract class AST implements IAST {
    public String getClassShortName() {
        return this.getClass().getName()
                .replaceFirst("^com.paracamplus.ilp1.", "");
    }
}
