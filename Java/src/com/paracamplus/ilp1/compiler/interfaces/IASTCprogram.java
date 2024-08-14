/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.interfaces;

import java.util.Set;

import com.paracamplus.ilp1.interfaces.IASTprogram;

public interface IASTCprogram extends IASTprogram {
    Set<IASTCglobalVariable> getGlobalVariables();
    void setGlobalVariables(Set<IASTCglobalVariable> gvs);

}
