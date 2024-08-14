package com.paracamplus.ilp4.ilp4tme10.interfaces;

public interface IASTvisitorTME10<Result, Data, Anomaly extends Throwable>
	extends com.paracamplus.ilp4.interfaces.IASTvisitor<Result,Data,Anomaly> {	
	Result visit(IASTexists iast, Data data) throws Anomaly;
	Result visit(IASTdefined iast, Data data) throws Anomaly;	
}
