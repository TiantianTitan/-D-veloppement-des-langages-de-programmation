/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

public class ASTsend implements IASTsend {

    public ASTsend (String messageName, 
                    IASTexpression receiver, 
                    IASTexpression[] arguments) {
        this.messageName = messageName;
        this.receiver = receiver;
        this.arguments = arguments;
    }
    private final String messageName;
    private final IASTexpression receiver;
    private final IASTexpression[] arguments;
    
    @Override
	public String getMethodName() {
        return messageName;
    }

    @Override
	public IASTexpression getReceiver() {
        return receiver;
    }

    @Override
	public IASTexpression[] getArguments() {
        return arguments;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
        return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }
}
