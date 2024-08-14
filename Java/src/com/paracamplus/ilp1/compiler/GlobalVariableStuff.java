/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler;

import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;

public class GlobalVariableStuff {
    public static void fillGlobalVariables (IGlobalVariableEnvironment env) {
        env.addGlobalVariableValue("pi", "ILP_PI");
        env.addGlobalFunctionValue(
                new Primitive("print", "ILP_print", 1));
        env.addGlobalFunctionValue(
                new Primitive("newline", "ILP_newline", 0));
        env.addGlobalFunctionValue(
                new Primitive("to_string", "ILP_to_string", 1));
       env.addGlobalFunctionValue(
                new Primitive("type_of", "ILP_type_of", 1));
    }
}
