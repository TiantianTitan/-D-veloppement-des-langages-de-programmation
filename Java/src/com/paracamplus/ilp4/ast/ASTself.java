/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.ast.ASTvariable;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTself extends ASTvariable implements IASTself {

    public ASTself () {
        super("self");
    }

    public <Result, Data, Anomaly extends Throwable> Result 
        accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
                throws Anomaly {
        return visitor.visit(this, data);
    }
}
