package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitorTME8;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

public class ASTwriteProperty extends ASTexpression implements IASTwriteProperty {

    private final IASTexpression property;
    private final IASTexpression target;
    private final IASTexpression value;

    public ASTwriteProperty(IASTexpression property, IASTexpression target, IASTexpression value) {
        this.property = property;
        this.target = target;
        this.value = value;
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
    public IASTexpression getValue() {
        return value;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data) throws Anomaly {
        return ((IASTvisitorTME8<Result, Data, Anomaly>)visitor).visit(this,data);
    }
}
