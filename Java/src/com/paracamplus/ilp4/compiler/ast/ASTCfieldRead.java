/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.ast;

import com.paracamplus.ilp4.ast.ASTfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTCfieldRead extends ASTfieldRead
implements IASTCfieldRead {
    
    public ASTCfieldRead (IASTCclassDefinition clazz,
                          String fieldName, 
                          IASTexpression target) {
        super(fieldName, target);
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
