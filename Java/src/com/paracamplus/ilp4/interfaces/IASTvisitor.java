/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interfaces;

public interface IASTvisitor<Result, Data, Anomaly extends Throwable>
extends com.paracamplus.ilp3.interfaces.IASTvisitor<Result, Data, Anomaly>{

    Result visit(IASTinstantiation iast, Data data) throws Anomaly;
    Result visit(IASTfieldRead iast, Data data) throws Anomaly;
    Result visit(IASTself iast, Data data) throws Anomaly;
    Result visit(IASTsend iast, Data data) throws Anomaly;
    Result visit(IASTsuper iast, Data data) throws Anomaly;
    Result visit(IASTfieldWrite iast, Data data) throws Anomaly;
}
