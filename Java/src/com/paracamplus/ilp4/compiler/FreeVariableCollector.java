/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler;


import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldWrite;
import com.paracamplus.ilp4.compiler.interfaces.IASTCinstantiation;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
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

public class FreeVariableCollector 
extends com.paracamplus.ilp3.compiler.FreeVariableCollector
implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

    
    public FreeVariableCollector(IASTCprogram program) {
        super(program);
    }

    
    @Override
	public IASTCprogram analyze () 
            throws CompilationException {
        for ( IASTfunctionDefinition ifd : ((IASTCprogram) program).getFunctionDefinitions() ) {
            Set<IASTClocalVariable> newvars = new HashSet<>();
            visit(ifd, newvars);
        }
        Set<IASTClocalVariable> newvars = new HashSet<>();
        ((IASTCprogram) program).getBody().accept(this, newvars);
        return (IASTCprogram) program;
    }
    
 

    // Class related 
    
    @Override
	public Void visit(IASTinstantiation iast, 
                      Set<IASTClocalVariable> variables)
                    throws CompilationException {
        for ( IASTexpression expression : iast.getArguments() ) {
            expression.accept(this, variables);
        }
        return null;
    }
    public Void visit(IASTCinstantiation iast, 
                      Set<IASTClocalVariable> variables)
            throws CompilationException {
       return visit((IASTinstantiation)iast, variables);
    }

    @Override
	public Void visit(IASTfieldRead iast, 
            Set<IASTClocalVariable> variables)
                    throws CompilationException {
        iast.getTarget().accept(this, variables);
        return null;
    }
    @Override
	public Void visit(IASTCfieldRead iast, 
                      Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTfieldRead)iast, variables);
    }

    @Override
	public Void visit(IASTfieldWrite iast, 
            Set<IASTClocalVariable> variables)
                    throws CompilationException {
        iast.getTarget().accept(this, variables);
        iast.getValue().accept(this, variables);
        return null;
    }
    @Override
	public Void visit(IASTCfieldWrite iast, 
                      Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTfieldWrite)iast, variables);
    }
    
    @Override
	public Void visit(IASTsend iast, 
                      Set<IASTClocalVariable> variables) 
            throws CompilationException {
        iast.getReceiver().accept(this, variables);
        for ( IASTexpression expression : iast.getArguments() ) {
            expression.accept(this, variables);
        }
        return null;
    }
    
    @Override
	public Void visit(IASTself iast, 
                      Set<IASTClocalVariable> variables) 
        throws CompilationException {
        return null;
    }
    
    @Override
	public Void visit(IASTsuper iast, 
                      Set<IASTClocalVariable> variables) 
        throws CompilationException {
        return null;
    }
}
