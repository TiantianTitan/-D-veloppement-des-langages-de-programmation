/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.interfaces;

import java.util.Set;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;


public interface IASTCprogram extends com.paracamplus.ilp1.compiler.interfaces.IASTCprogram {
    @Override
	Set<IASTCglobalVariable> getGlobalVariables();
    @Override
	void setGlobalVariables(Set<IASTCglobalVariable> gvs);
   // Covariance
    IASTCfunctionDefinition[] getFunctionDefinitions();
}
