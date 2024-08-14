/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvisitable;

public interface IASTCvisitable extends IASTvisitable {
    <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTCvisitor<Result, Data, Anomaly> visitor,
                  Data data) throws Anomaly;
}
