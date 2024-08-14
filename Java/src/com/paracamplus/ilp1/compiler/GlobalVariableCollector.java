/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import java.util.HashSet;
import java.util.Set;


import com.paracamplus.ilp1.compiler.interfaces.IASTCcomputedInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class GlobalVariableCollector 
implements IASTCvisitor<Set<IASTCglobalVariable>, 
                        Set<IASTCglobalVariable>, 
                        CompilationException> {

    public GlobalVariableCollector () {
        this.result = new HashSet<>();
    }
    protected Set<IASTCglobalVariable> result;
    
    public Set<IASTCglobalVariable> analyze(IASTCprogram program) 
            throws CompilationException {
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
            result = visit(gv, result);
        }
        return result;
    }
   
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCglobalVariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result.add(iast);
        return result;
    }

    @Override
	public Set<IASTCglobalVariable> visit(
            IASTClocalVariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCvariable iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        throw new RuntimeException("Should not occur");
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTalternative iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getCondition().accept(this, result);
        result = iast.getConsequence().accept(this, result);
        result = iast.getAlternant().accept(this, result);
        return result;
    }
    
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTbinaryOperation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        iast.getLeftOperand().accept(this, result);
        iast.getRightOperand().accept(this, result);
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTunaryOperation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        iast.getOperand().accept(this, result);
        return result;
    }
    
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTboolean iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTfloat iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTinteger iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTstring iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return result;
    }

    @Override
	public Set<IASTCglobalVariable> visit(
            IASTblock iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        for ( IASTbinding binding : iast.getBindings() ) {
            result = binding.getInitialisation().accept(this, result);
        }
        result = iast.getBody().accept(this, result);
        return result;
    }
       
    @Override
	public Set<IASTCglobalVariable> visit(
            IASTinvocation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        result = iast.getFunction().accept(this, result);
        for ( IASTexpression arg : iast.getArguments() ) {
            result = arg.accept(this, result);
        }
        return result;
    }

    @Override
	public Set<IASTCglobalVariable> visit(
            IASTCcomputedInvocation iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        return visit((IASTinvocation) iast, result);
    }

    @Override
	public Set<IASTCglobalVariable> visit(
            IASTsequence iast,
            Set<IASTCglobalVariable> result) 
                    throws CompilationException {
        for ( IASTexpression expr : iast.getExpressions() ) {
            result = expr.accept(this, result);
        }
        return result;
    }
    
   
}
