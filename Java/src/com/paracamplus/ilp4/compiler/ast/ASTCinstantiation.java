/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.ast;

import com.paracamplus.ilp4.ast.ASTinstantiation;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCinstantiation;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTCinstantiation extends ASTinstantiation
implements IASTCinstantiation {
    public ASTCinstantiation(IASTCclassDefinition clazz,
                             IASTexpression[] arguments) {
        super(clazz.getName(), arguments);
        this.clazz = clazz;
    }
    private final IASTCclassDefinition clazz;

    @Override
	public IASTCclassDefinition getDefiningClass () {
        return clazz;
    }
    
    @Override
    public <Result, Data, Anomaly extends Throwable> Result 
    accept(com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return ((IASTvisitor<Result,Data,Anomaly>)visitor).visit(this, data);
    }
}
