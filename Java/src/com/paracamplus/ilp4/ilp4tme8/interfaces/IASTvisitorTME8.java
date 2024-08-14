package com.paracamplus.ilp4.ilp4tme8.interfaces;

public interface IASTvisitorTME8<Result, Data, Anomaly extends Throwable>
        extends com.paracamplus.ilp4.interfaces.IASTvisitor<Result, Data, Anomaly>  {
    Result visit(IASTreadProperty iast, Data data) throws Anomaly;
    Result visit(IASTwriteProperty iast, Data data) throws Anomaly;
    Result visit(IASThasProperty iast, Data data) throws Anomaly;
}
