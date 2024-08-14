/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.compiler.interfaces;

import com.paracamplus.ilp1.compiler.interfaces.IClosable;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;

public interface IASTCfunctionDefinition 
extends IASTfunctionDefinition, IClosable {
    String getCName();
}
