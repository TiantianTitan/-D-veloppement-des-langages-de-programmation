package com.paracamplus.ilp3.parser.ilpml;

import com.paracamplus.ilp1.tools.MLPrinter;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp3.interfaces.IASTvisitor;

/* 
 * Affichage d'un AST sous forme ILML, en utilisant un visiteur.
 */
public class ILPMLProgramPrinter
extends com.paracamplus.ilp2.parser.ilpml.ILPMLProgramPrinter
implements IASTvisitor<Object,MLPrinter,Exception> {
	
	public String getString() throws Exception {
		MLPrinter printer = new MLPrinter();
		visit((IASTprogram)input,printer);
		return printer.getContents();
	}

	
	// Visite des déclarations de haut niveau

	
	// Implantation des méthodes du visiteur IASTvisitable
	
	@Override
	public Object visit(IASTcodefinitions iast, MLPrinter printer) throws Exception {
		if (iast.getFunctions().length == 0) {
			iast.getBody().accept(this,printer);
		}
		else {
			printer.openParen("(");	
			boolean first = true;
			for (IASTnamedLambda f : iast.getFunctions()) {
				printer.insertBreak();
				if (first) first = false;	else printer.append("and ");
				printer.append("function " + f.getFunctionVariable().getMangledName() + "(");
				appendVariables(f.getVariables(), ", ", printer);
				printer.append(")");
				printer.insertBreak();
				f.getBody().accept(this,printer);
			}
			printer.append(" in");
			printer.insertBreak();
			iast.getBody().accept(this,printer);
			printer.closeParen(")");
		}
		return null;
	}
	
	@Override
	public Object visit(IASTlambda iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		printer.append("lambda (");
		appendVariables(iast.getVariables(), ", ", printer);
		printer.append(") ");
		iast.getBody().accept(this,printer);
		printer.closeParen(")");
		return null;
	}

	@Override
	public Object visit(IASTtry iast, MLPrinter printer) throws Exception {
		printer.openBreakParen("(");
		printer.append("try ");
		iast.getBody().accept(this,printer);
		IASTlambda c = iast.getCatcher();
		if (c != null) {
			// Le catcher est une lambda-expression à une seule variable
			assert(c.getVariables().length == 1);
			String e = c.getVariables()[0].getMangledName();
			printer.insertBreak();
			printer.append("catch ("+e+") ");
			c.getBody().accept(this,printer);
		}
		if (iast.getFinallyer() != null) {
			printer.insertBreak();
			printer.append("finally ");
			iast.getFinallyer().accept(this,printer);
		}
		printer.closeBreakParen(")");
		return null;
	}

}
