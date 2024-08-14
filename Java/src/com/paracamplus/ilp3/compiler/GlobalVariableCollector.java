/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler;

import java.util.Set;

import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTtry;

public class GlobalVariableCollector
extends com.paracamplus.ilp2.compiler.GlobalVariableCollector
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
            IASTvariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        if ( iast instanceof IASTCglobalVariable ) {
            IASTCglobalVariable gv = (IASTCglobalVariable) iast;
            if ( ! ( gv instanceof IASTClocalFunctionVariable ) ) {
                result = visit(gv, result);
            }
        }
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTClocalFunctionVariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
  
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTcodefinitions iast,
            Set<IASTCglobalVariable> result)
                    throws CompilationException {
        for ( IASTnamedLambda ifd : iast.getFunctions() ) {
            result = ifd.getBody().accept(this, result);
        }
        result = iast.getBody().accept(this, result);
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCcodefinitions iast,
            Set<IASTCglobalVariable> result)
                    throws CompilationException {
        return visit((IASTcodefinitions)iast, result);
    }
    

    @Override
	public Set<IASTCglobalVariable> visit(
            IASTClocalFunctionInvocation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return visit((IASTinvocation) iast, result);
    }
 
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTlambda iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getBody().accept(this, result);
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTClambda iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return visit((IASTlambda)iast, result);
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTtry iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getBody().accept(this, result);
        IASTlambda catcher = iast.getCatcher();
        if ( catcher != null ) {
            result = catcher.accept(this, result);
        }
        IASTexpression finallyer = iast.getFinallyer();
        if ( finallyer != null ) {
            result = finallyer.accept(this, result);
        }
        return result;
    }
    
   
}
