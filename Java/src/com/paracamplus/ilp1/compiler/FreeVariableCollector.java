/* *****************************************************************
 * ilp1 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp1
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;


import java.util.HashSet;
import java.util.Set;


import com.paracamplus.ilp1.compiler.interfaces.IASTCblock;
import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class FreeVariableCollector 
implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

    public FreeVariableCollector(IASTCprogram program) {
        this.program = program;
    }
    protected final IASTCprogram program;
    
    public IASTCprogram analyze () 
            throws CompilationException {
        Set<IASTClocalVariable> newvars = new HashSet<>();
        program.getBody().accept(this, newvars);
        return program;
    }
    
    @Override
	public Void visit(IASTvariable iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        if ( iast instanceof IASTClocalVariable ) {
            variables.add((IASTClocalVariable)iast);
        }
        return null;
    }

    @Override
	public Void visit(IASTCglobalVariable iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        return null;
    }

    @Override
	public Void visit(IASTClocalVariable iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        variables.add(iast);
        return null;
    }
    @Override
	public Void visit(IASTCvariable iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        return null;
    }
    

    
    @Override
	public Void visit(IASTalternative iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        iast.getCondition().accept(this, variables);
        iast.getConsequence().accept(this, variables);
        iast.getAlternant().accept(this, variables);
        return null;
    }
    
    @Override
	public Void visit(IASTbinaryOperation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        iast.getLeftOperand().accept(this, variables);
        iast.getRightOperand().accept(this, variables);
        return null;
    }
    @Override
	public Void visit(IASTunaryOperation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        iast.getOperand().accept(this, variables);
        return null;
    }
    @Override
	public Void visit(IASTboolean iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }
    @Override
	public Void visit(IASTfloat iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }
    @Override
	public Void visit(IASTinteger iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }
    @Override
	public Void visit(IASTstring iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }
    
    @Override
	public Void visit(IASTinvocation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        iast.getFunction().accept(this, variables);
        for ( IASTexpression expression : iast.getArguments() ) {
            expression.accept(this, variables);
        }
        return null;
    }

    @Override
	public Void visit(IASTCcomputedInvocation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTinvocation) iast, variables);
    }
    
    
    @Override
	public Void visit(IASTsequence iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        for ( IASTexpression expression : iast.getExpressions() ) {
            expression.accept(this, variables);
        }
        return null;
    }
    
    
    @Override
	public Void visit(IASTblock iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        if ( iast instanceof IASTCblock ) {
            return visit((IASTCblock) iast, variables);
        } else {
            throw new RuntimeException("should not occur");
        }
    }
    public Void visit(IASTCblock iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        Set<IASTClocalVariable> currentVars = new HashSet<>();
        for ( IASTCblock.IASTCbinding binding : iast.getBindings() ) {
            binding.getInitialisation().accept(this, variables);
            currentVars.add(binding.getVariable());
        }
        Set<IASTClocalVariable> newvars = new HashSet<>();
        iast.getBody().accept(this, newvars);
        newvars.removeAll(currentVars);
        variables.addAll(newvars);
        return null;
    }
    
  
 
}
