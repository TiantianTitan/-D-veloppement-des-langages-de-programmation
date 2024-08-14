/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.interfaces;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
extends com.paracamplus.ilp2.interfaces.IASTvisitor<Result, Data, Anomaly>
 {
    Result visit(IASTCglobalFunctionVariable iast, Data data) throws Anomaly;
    Result visit(IASTCglobalInvocation iast, Data data) throws Anomaly;
}
