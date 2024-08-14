/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp2.interfaces.IASTloop;

public class GlobalVariableCollector extends com.paracamplus.ilp1.compiler.GlobalVariableCollector
implements IASTCvisitor<Set<IASTCglobalVariable>, 
                        Set<IASTCglobalVariable>, 
                        CompilationException> {


    
    public Set<IASTCglobalVariable> analyze(IASTCprogram program) 
            throws CompilationException {
    	for ( IASTfunctionDefinition ifd : program.getFunctionDefinitions() ) {
           result = ifd.getBody().accept(this, result);
        }
    	result = program.getBody().accept(this, result);
        return result;
    }

 
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCglobalFunctionVariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }

    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTassignment iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getVariable().accept(this, result);
        result = iast.getExpression().accept(this, result);
        return result;
    }
    
   
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCglobalInvocation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return visit((IASTinvocation) iast, result);
    }
     
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTloop iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getCondition().accept(this, result);
        result = iast.getBody().accept(this, result);
        return result;
    }
    
   
}
