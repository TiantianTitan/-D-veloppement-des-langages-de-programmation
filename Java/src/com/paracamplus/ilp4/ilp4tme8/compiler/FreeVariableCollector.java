package com.paracamplus.ilp4.ilp4tme8.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.ilp4tme8.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

import java.util.Set;

public class FreeVariableCollector extends com.paracamplus.ilp4.compiler.FreeVariableCollector
    implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

    public FreeVariableCollector(IASTCprogram program) {
        super(program);
    }

    @Override
    public Void visit(IASTreadProperty iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getProperty().accept(this, variables);
        iast.getTarget().accept(this, variables);
        return null;
    }

    @Override
    public Void visit(IASTwriteProperty iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getProperty().accept(this, variables);
        iast.getTarget().accept(this, variables);
        iast.getValue().accept(this, variables);
        return null;
    }

    @Override
    public Void visit(IASThasProperty iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getProperty().accept(this, variables);
        iast.getTarget().accept(this, variables);
        return null;
    }
}
