package com.paracamplus.ilp2.parser.ilpml;

import com.paracamplus.ilp1.tools.MLPrinter;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

/* 
 * Affichage d'un AST sous forme ILML, en utilisant un visiteur.
 */
public class ILPMLProgramPrinter
extends com.paracamplus.ilp1.parser.ilpml.ILPMLProgramPrinter
implements IASTvisitor<Object,MLPrinter,Exception> {
	
	public String getString() throws Exception {
		MLPrinter printer = new MLPrinter();
		visit((IASTprogram)input,printer);
		return printer.getContents();
	}

	
	// Visite des déclarations de haut niveau

	public Object visit(IASTprogram iast, MLPrinter printer) throws Exception {
		for (IASTfunctionDefinition d : iast.getFunctionDefinitions()) {
			visit(d,printer);
		}
		iast.getBody().accept(this,null);
		printer.insertBreak();
		return null;
	}
	
	public Object visit(IASTfunctionDefinition iast, MLPrinter printer) throws Exception {
		printer.insertBreak();
		printer.append("function ");
		printer.append(iast.getMangledName());
		printer.append("(");
		appendVariables(iast.getVariables(), ", ", printer);
		printer.append(")");
		printer.insertBreak();
		iast.getBody().accept(this,printer);
		printer.append(";");
		printer.insertBreak();
		return null;
	}
	
	// Implantation des méthodes du visiteur IASTvisitable
	
	@Override
	public Object visit(IASTassignment iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		printer.append(iast.getVariable().getMangledName());
		printer.append(" = ");
		printer.indent();
		iast.getExpression().accept(this,printer);
		printer.dedent();
		printer.closeParen(")");
		return null;
	}

	@Override
	public Object visit(IASTloop iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		printer.append("while ");
		printer.indent();
		iast.getCondition().accept(this,printer);
		printer.dedent();
		printer.append(" do");
		printer.insertBreak();
		iast.getBody().accept(this,printer);
		printer.closeParen(")");
		return null;
	}

}
