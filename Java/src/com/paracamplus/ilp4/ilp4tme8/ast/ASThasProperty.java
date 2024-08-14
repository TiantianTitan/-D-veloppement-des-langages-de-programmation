package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;

import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitorTME8;

public class ASThasProperty extends ASTexpression implements IASThasProperty {

    private final IASTexpression property;
    private final IASTexpression target;

    public ASThasProperty(IASTexpression property, IASTexpression target) {
        this.property = property;
        this.target = target;
    }

    @Override
    public IASTexpression getTarget() {
        return target;
    }

    @Override
    public IASTexpression getProperty() {
        return property;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data) throws Anomaly {
        return ((IASTvisitorTME8<Result, Data, Anomaly>)visitor).visit(this,data);
    }
}
