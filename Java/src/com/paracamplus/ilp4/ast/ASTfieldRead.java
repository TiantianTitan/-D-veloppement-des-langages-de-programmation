/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTfieldRead extends ASTexpression
implements IASTfieldRead {

    public ASTfieldRead (String fieldName, IASTexpression target) {
        this.fieldName = fieldName;
        this.target = target;
    }
    private final String fieldName;
    private final IASTexpression target;
    
    @Override
	public IASTexpression getTarget() {
        return target;
    }

    @Override
	public String getFieldName() {
        return fieldName;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
        return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }
}
