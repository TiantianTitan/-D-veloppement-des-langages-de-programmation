/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTblock extends ASTexpression implements IASTblock {
    
    public static class ASTbinding extends AST implements IASTbinding {
        public ASTbinding (IASTvariable variable, IASTexpression initialisation) {
            this.variable = variable;
            this.initialisation = initialisation;
        }
        private final IASTvariable variable;
        private final IASTexpression initialisation;
        
        @Override
		public IASTvariable getVariable () {
            return variable;
        }
        @Override
		public IASTexpression getInitialisation () {
            return initialisation;
        }
    }
    
    public ASTblock (IASTbinding[] binding,
                     IASTexpression body ) {
        this.binding = binding;
        this.body = body;
    }
    private final IASTbinding[] binding;
    private final IASTexpression body;
    
    @Override
	public IASTbinding[] getBindings() {
        return binding;
    }
    
    @Override
	public IASTexpression getBody() {
        return body;
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
}
