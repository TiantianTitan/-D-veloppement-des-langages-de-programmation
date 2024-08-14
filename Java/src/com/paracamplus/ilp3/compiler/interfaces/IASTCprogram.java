/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.compiler.interfaces;

import java.util.List;


public interface IASTCprogram 
extends com.paracamplus.ilp2.compiler.interfaces.IASTCprogram {
    void addClosureDefinition(IASTClambda f);
    List<IASTClambda> getClosureDefinitions ();
}
