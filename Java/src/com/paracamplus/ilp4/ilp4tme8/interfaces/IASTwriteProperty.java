package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTwriteProperty extends IASTexpression {
    IASTexpression getTarget();
    IASTexpression getProperty();
    IASTexpression getValue();
}
