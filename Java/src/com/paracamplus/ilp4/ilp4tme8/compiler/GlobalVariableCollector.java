package com.paracamplus.ilp4.ilp4tme8.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.ilp4tme8.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

import java.util.Set;

public class GlobalVariableCollector extends com.paracamplus.ilp4.compiler.GlobalVariableCollector
    implements IASTCvisitor<Set<IASTCglobalVariable>,
        Set<IASTCglobalVariable>,
        CompilationException> {

    @Override
    public Set<IASTCglobalVariable> visit(IASTreadProperty iast, Set<IASTCglobalVariable> variables) throws CompilationException {
        variables = iast.getProperty().accept(this, variables);
        variables = iast.getTarget().accept(this, variables);
        return variables;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASTwriteProperty iast, Set<IASTCglobalVariable> variables) throws CompilationException {
        variables = iast.getProperty().accept(this, variables);
        variables = iast.getTarget().accept(this, variables);
        variables = iast.getValue().accept(this, variables);
        return variables;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASThasProperty iast, Set<IASTCglobalVariable> variables) throws CompilationException {
        variables = iast.getProperty().accept(this, variables);
        variables = iast.getTarget().accept(this, variables);
        return variables;
    }
}
