/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.interfaces;

import com.paracamplus.ilp4.interfaces.IASTvisitor;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
extends IASTvisitor<Result, Data, Anomaly> {
    Result visit(IASTCfieldRead iast, Data data) throws Anomaly;
    Result visit(IASTCfieldWrite iast, Data data) throws Anomaly;
}
