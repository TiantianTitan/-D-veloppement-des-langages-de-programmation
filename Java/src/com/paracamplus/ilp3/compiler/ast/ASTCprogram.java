/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.ast;


import java.util.List;
import java.util.Vector;


import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTCprogram 
extends com.paracamplus.ilp2.compiler.ast.ASTCprogram 
implements IASTCprogram {

    public ASTCprogram (IASTCfunctionDefinition[] functions, 
                        IASTexpression expression) {
        super(functions,  expression);
        this.closureDefinitions = new Vector<>();
    }
    protected List<IASTClambda> closureDefinitions;
    


    @Override
	public void addClosureDefinition(IASTClambda f) {
        closureDefinitions.add(f);       
    }
    
    @Override
	public List<IASTClambda> getClosureDefinitions () {
        return closureDefinitions;
    }
    
}
