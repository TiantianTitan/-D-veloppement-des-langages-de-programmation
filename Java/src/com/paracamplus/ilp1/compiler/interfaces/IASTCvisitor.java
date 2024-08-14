/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvisitor;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
extends IASTvisitor<Result, Data, Anomaly> {
    Result visit(IASTCglobalVariable iast, Data data) throws Anomaly;
    Result visit(IASTClocalVariable iast, Data data) throws Anomaly;
    Result visit(IASTCvariable iast, Data data) throws Anomaly;
    Result visit(IASTCcomputedInvocation iast, Data data) throws Anomaly;
}
