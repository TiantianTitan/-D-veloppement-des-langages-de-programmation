package com.paracamplus.ilp4.ilp4tme10.compiler.interfaces;

import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitorTME10;

public interface IASTCvisitorTME10<Result, Data, Anomaly extends Throwable>
    extends com.paracamplus.ilp4.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly>,
		IASTvisitorTME10<Result, Data, Anomaly> {

	Result visit(IASTexists iast, Data data) throws Anomaly;
	Result visit(IASTdefined iast, Data data) throws Anomaly;

}
