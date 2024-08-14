package com.paracamplus.ilp4.ilp4tme8.compiler.normalizer;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitorTME8;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

public class NormalizerTME8 extends com.paracamplus.ilp4.compiler.normalizer.Normalizer
    implements IASTvisitorTME8<IASTexpression, INormalizationEnvironment, CompilationException> {

    protected INormalizationFactory factory;

    public NormalizerTME8(INormalizationFactory factory, IASTCclassDefinition objectClass) {
        super(factory, objectClass);
        this.factory = factory;
    }

    @Override
    public IASTexpression visit(IASTreadProperty iast, INormalizationEnvironment environment) throws CompilationException {
        IASTexpression property = iast.getProperty().accept(this, environment);
        IASTexpression target = iast.getTarget().accept(this, environment);
        return factory.newReadProperty(property, target);
    }

    @Override
    public IASTexpression visit(IASTwriteProperty iast, INormalizationEnvironment environment) throws CompilationException {
        IASTexpression property = iast.getProperty().accept(this, environment);
        IASTexpression target = iast.getTarget().accept(this, environment);
        IASTexpression value = iast.getValue().accept(this, environment);
        return factory.newWriteProperty(property, target, value);
    }

    @Override
    public IASTexpression visit(IASThasProperty iast, INormalizationEnvironment environment) throws CompilationException {
        IASTexpression property = iast.getProperty().accept(this, environment);
        IASTexpression target = iast.getTarget().accept(this, environment);
        return factory.newHasProperty(property, target);
    }
}
