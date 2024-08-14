/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public class FreeVariableCollector 
extends com.paracamplus.ilp2.compiler.FreeVariableCollector
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
	public Void visit(IASTClocalFunctionVariable iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        return null;
    }

    
    @Override
	public Void visit(IASTClocalFunctionInvocation iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTinvocation) iast, variables);
    }
    
  
    @Override
	public Void visit(IASTtry iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getBody().accept(this, variables);
        IASTlambda catcher = iast.getCatcher();
        if ( catcher != null ) {
            Set<IASTClocalVariable> newvars = new HashSet<>();
            catcher.getBody().accept(this, newvars);
            newvars.remove(catcher.getVariables()[0]);
            variables.addAll(newvars);
        }
        IASTexpression finallyer = iast.getFinallyer();
        if ( finallyer != null ) {
            finallyer.accept(this, variables);
        }
        return null;
    }
    
    
    @Override
	public Void visit(IASTlambda iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        Set<IASTClocalVariable> newvars = new HashSet<>();
        iast.getBody().accept(this, newvars);
        IASTvariable[] vars = iast.getVariables();
        newvars.removeAll(Arrays.asList(vars));
        try {
            // Cast ensured by normalizer:
            IASTClambda f = (IASTClambda) iast;
            f.setClosedVariables(newvars);
            ((IASTCprogram) program).addClosureDefinition(f);
            for ( IASTvariable v : newvars ) {
                // Cast ensured by normalizer:
                ((IASTClocalVariable)v).setClosed();
            }
        } catch (ClassCastException exc) {
            throw new RuntimeException("should not occur");
        }
        variables.addAll(newvars);
        return null;
    }
    
    @Override
	public Void visit(IASTClambda iast, Set<IASTClocalVariable> variables) 
            throws CompilationException {
        Set<IASTClocalVariable> newvars = new HashSet<>();
        iast.getBody().accept(this, newvars);
        IASTvariable[] vars = iast.getVariables();
        newvars.removeAll(Arrays.asList(vars));
        try {
            iast.setClosedVariables(newvars);
            ((IASTCprogram) program).addClosureDefinition(iast);
            for ( IASTvariable v : newvars ) {
                // Cast ensured by normalizer:
                ((IASTClocalVariable)v).setClosed();
            }
        } catch (ClassCastException exc) {
            throw new RuntimeException("should not occur");
        }
        variables.addAll(newvars);
        return null;
    }
    
    @Override
	public Void visit(IASTcodefinitions iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        IASTnamedLambda[] functions = iast.getFunctions();
        // Collect the names of the local functions:
        List<IASTvariable> functionsVariables = new Vector<>(); 
        for ( IASTnamedLambda ifd : functions ) {
            functionsVariables.add(ifd.getFunctionVariable());
        }
        for ( IASTnamedLambda ifd : functions ) {
            Set<IASTClocalVariable> newvars = new HashSet<>();
            visit(ifd, newvars);
            IASTvariable[] vars = ifd.getVariables();
            newvars.removeAll(Arrays.asList(vars));
            try {
                // Cast ensured by normalizer:
                IASTClambda fun = (IASTClambda) ifd;
                fun.setClosedVariables(newvars);
                for ( IASTvariable v : newvars ) {
                    // Cast ensured by normalizer:
                    ((IASTClocalVariable)v).setClosed();
                }
            } catch (ClassCastException exc) {
                throw new RuntimeException("should not occur");
            }
            newvars.removeAll(functionsVariables);
            variables.addAll(newvars);
        }
        for ( IASTnamedLambda ifd : functions ) {
            try {
                // Cast ensured by normalizer:
                IASTClocalFunctionVariable v = 
                    (IASTClocalFunctionVariable) ifd.getFunctionVariable();
                v.setClosed();
            } catch (ClassCastException exc) {
                throw new RuntimeException("should not occur");
            }
        }
        iast.getBody().accept(this, variables);
        variables.removeAll(functionsVariables);
        return null;
    }
    @Override
	public Void visit(IASTCcodefinitions iast, Set<IASTClocalVariable> variables)
            throws CompilationException {
        return visit((IASTcodefinitions) iast, variables); 
    }

}
