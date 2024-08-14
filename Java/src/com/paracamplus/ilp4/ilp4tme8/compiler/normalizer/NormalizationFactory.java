package com.paracamplus.ilp4.ilp4tme8.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.ilp4tme8.ast.ASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTwriteProperty;

public class NormalizationFactory extends com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory
    implements INormalizationFactory{

    @Override
    public IASTexpression newReadProperty(IASTexpression property, IASTexpression target) {
        return new ASTreadProperty(property, target);
    }

    @Override
    public IASTexpression newWriteProperty(IASTexpression property, IASTexpression target, IASTexpression value) {
        return new ASTwriteProperty(property, target, value);
    }

    @Override
    public IASTexpression newHasProperty(IASTexpression property, IASTexpression target) {
        return new ASThasProperty(property, target);
    }
}
