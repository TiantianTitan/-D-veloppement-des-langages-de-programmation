/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.interfaces;

import com.paracamplus.ilp3.interfaces.IASTvisitor;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
extends IASTvisitor<Result, Data, Anomaly>, com.paracamplus.ilp2.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly>
 {
    Result visit(IASTCcodefinitions iast, Data data) throws Anomaly;
    Result visit(IASTClambda iast, Data data) throws Anomaly;
    Result visit(IASTClocalFunctionInvocation iast, Data data) throws Anomaly;
    Result visit(IASTClocalFunctionVariable iast, Data data) throws Anomaly;
}
