package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTfactoryTME8 extends com.paracamplus.ilp4.interfaces.IASTfactory {
    IASTexpression newReadProperty(IASTexpression property, IASTexpression target);
    IASTexpression newWriteProperty(IASTexpression property, IASTexpression target, IASTexpression value);
    IASTexpression newHasProperty(IASTexpression property, IASTexpression target);
}
