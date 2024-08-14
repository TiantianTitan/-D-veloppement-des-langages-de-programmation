/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

public interface IMethod extends Invocable {
    String getName();
    IClass getDefiningClass();
    void setDefiningClass(IClass clazz);
    int getMethodArity();
}
