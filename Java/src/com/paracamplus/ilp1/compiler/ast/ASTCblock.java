/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.ast;

import com.paracamplus.ilp1.ast.ASTblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTCblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class ASTCblock extends ASTblock implements IASTCblock {

    public static class ASTCbinding extends ASTblock.ASTbinding 
    implements IASTCbinding {
        public ASTCbinding (IASTvariable variable, IASTexpression initialisation) {
            super(variable, initialisation);
        }
        @Override
		public IASTClocalVariable getVariable () {
            return (IASTClocalVariable) super.getVariable();
        }
    }
   
    public ASTCblock (IASTCbinding[] binding,
                      IASTexpression body ) {
        super(binding, body);
    }

    @Override
    public IASTCbinding[] getBindings() {
        return (IASTCbinding[]) super.getBindings();
    }
    
    public <Result, Data, Anomaly extends Throwable> Result accept(
            IASTCvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
}
