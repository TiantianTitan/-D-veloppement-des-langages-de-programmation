/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.interfaces;

import com.paracamplus.ilp3.interfaces.IASTcodefinitions;

public interface IASTCcodefinitions extends IASTcodefinitions {
    @Override
	IASTCnamedLambda[] getFunctions();
}
