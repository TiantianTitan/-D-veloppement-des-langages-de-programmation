package com.paracamplus.ilp4.ilp4tme8.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface INormalizationFactory extends com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory {
    IASTexpression newReadProperty(IASTexpression property, IASTexpression target);
    IASTexpression newWriteProperty(IASTexpression property, IASTexpression target, IASTexpression value);
    IASTexpression newHasProperty(IASTexpression property, IASTexpression target);
}
