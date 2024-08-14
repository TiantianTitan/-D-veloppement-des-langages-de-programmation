/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class FreeVariableCollector extends com.paracamplus.ilp1.compiler.FreeVariableCollector
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
    

   @Override
public Void visit(IASTassignment iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        IASTvariable variable = iast.getVariable();
        variable.accept(this, variables);
        iast.getExpression().accept(this, variables);
        try {
            // Cast ensured by normalizer:
            ((IASTCvariable) variable).setMutable();
        } catch (ClassCastException exc) {
            throw new RuntimeException("should never occur!");
        }
        return null;
    }
    
   @Override
public Void visit(IASTCglobalFunctionVariable iast, Set<IASTClocalVariable> variables) 
           throws CompilationException {
       return null;
   }

    
   @Override
public Void visit(IASTCglobalInvocation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTinvocation) iast, variables);
    }

   @Override
public Void visit(IASTloop iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getCondition().accept(this, variables);
        iast.getBody().accept(this, variables);
        return null;
    }
    
    public Void visit(IASTfunctionDefinition fd, 
            Set<IASTClocalVariable> variables) 
            throws CompilationException {
        Set<IASTClocalVariable> newvars = new HashSet<>();
        fd.getBody().accept(this, newvars);
        IASTvariable[] vars = fd.getVariables();
        newvars.removeAll(Arrays.asList(vars));
        try {
            // Cast ensured by normalizer:
            IASTCfunctionDefinition fun = (IASTCfunctionDefinition) fd;
            fun.setClosedVariables(newvars);
            for ( IASTvariable v : newvars ) {
                ((IASTClocalVariable)v).setClosed();
            }
        } catch (ClassCastException exc) {
            throw new RuntimeException("should not occur");
        }
        variables.addAll(newvars);
        return null;
    }
    
   
}
