/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.interfaces;

import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvisitable;
import com.paracamplus.ilp1.compiler.interfaces.IClosable;
import com.paracamplus.ilp1.interfaces.Inamed;

public interface IASTClambda 
extends IASTlambda, IClosable, Inamed, IASTCvisitable {
}
