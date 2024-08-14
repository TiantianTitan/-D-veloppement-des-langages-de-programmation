/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler;

import java.util.Set;

import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldWrite;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCinstantiation;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp1.compiler.CompilationException;

public class GlobalVariableCollector 
extends com.paracamplus.ilp3.compiler.GlobalVariableCollector
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

    
    // Class related

    @Override
	public Set<IASTCglobalVariable> visit(IASTinstantiation iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        if ( iast instanceof IASTCinstantiation ) {
            return visit((IASTCinstantiation) iast, result);
        } else {
            throw new CompilationException("should not occur");
        }
    }
    public Set<IASTCglobalVariable> visit(IASTCinstantiation iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        for ( IASTexpression expr : iast.getArguments() ) {
            result = expr.accept(this, result);
        }
        return result;
    }

    @Override
	public Set<IASTCglobalVariable> visit(IASTfieldRead iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        if ( iast instanceof IASTCfieldRead ) {
            return visit((IASTCfieldRead) iast, result);
        } else {
            throw new RuntimeException("Should not occur");
        }
    }
    @Override
	public Set<IASTCglobalVariable> visit(IASTCfieldRead iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getTarget().accept(this, result);
        return result;
    }

    @Override
	public Set<IASTCglobalVariable> visit(IASTfieldWrite iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        if ( iast instanceof IASTCfieldWrite ) {
            return visit((IASTCfieldWrite) iast, result);
        } else {
            throw new RuntimeException("Should not occur");
        }
    }
    @Override
	public Set<IASTCglobalVariable> visit(IASTCfieldWrite iast,
                                          Set<IASTCglobalVariable> result)
        throws CompilationException {
        result = iast.getTarget().accept(this, result);
        result = iast.getValue().accept(this, result);
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(IASTsend iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getReceiver().accept(this, result);
        for ( IASTexpression expr : iast.getArguments() ) {
            result = expr.accept(this, result);
        }
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(IASTself iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(IASTsuper iast,
                                          Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
}
