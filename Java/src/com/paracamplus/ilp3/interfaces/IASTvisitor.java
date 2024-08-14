/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.interfaces;

public interface IASTvisitor<Result, Data, Anomaly extends Throwable> 
extends com.paracamplus.ilp2.interfaces.IASTvisitor<Result, Data, Anomaly>
{
    Result visit(IASTcodefinitions iast, Data data) throws Anomaly;
    Result visit(IASTlambda iast, Data data) throws Anomaly;
    Result visit(IASTtry iast, Data data) throws Anomaly;
}
