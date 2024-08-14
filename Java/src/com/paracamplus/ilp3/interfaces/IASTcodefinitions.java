/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTcodefinitions extends IASTexpression {
    IASTnamedLambda[] getFunctions();
    IASTexpression getBody();
}
