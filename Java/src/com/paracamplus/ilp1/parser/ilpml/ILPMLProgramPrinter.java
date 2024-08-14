package com.paracamplus.ilp1.parser.ilpml;

import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp1.tools.MLPrinter;

/* 
 * Affichage d'un AST sous forme ILML, en utilisant un visiteur.
 */
public class ILPMLProgramPrinter
implements IASTvisitor<Object,MLPrinter,Exception> {
	
	protected IASTprogram input;

	public void setInput(IASTprogram input) {
		this.input = input;
	}
	
	public String getString() throws Exception {
		MLPrinter printer = new MLPrinter();
		visit((IASTprogram)input,printer);
		return printer.getContents();
	}
	
	protected void appendVariables(IASTvariable[] vars, String separator, MLPrinter printer) {
		boolean first = true;
		for (IASTvariable x : vars) {
			if (first) {
				first = false;
			}
			else {
				printer.append(separator);
			}
			printer.append(x.getMangledName());
		}
	}

	protected void appendExpressions(IASTexpression[] exp, String separator, MLPrinter printer) throws Exception  {
		boolean first = true;
		for (IASTexpression e : exp) {
			if (first) {
				first = false;
			}
			else {
				printer.append(separator);
			}
			e.accept(this,printer);
		}
	}

	
	// Visite des déclarations de haut niveau

	public Object visit(IASTprogram iast, MLPrinter printer) throws Exception {
		iast.getBody().accept(this,printer);
		printer.insertBreak();
		return null;
	}

	
	// Implantation des méthodes du visiteur IASTvisitable
	
	@Override
	public Object visit(IASTalternative iast, MLPrinter printer) throws Exception {
		printer.openBreakParen("(");
		printer.append("if ");
		printer.indent();
		iast.getCondition().accept(this,printer);
		printer.dedent();
		printer.insertBreak();
		printer.append("then ");
		printer.indent();
		iast.getConsequence().accept(this,printer);
		printer.dedent();
		if (iast.isTernary()) {
			printer.insertBreak();
			printer.append("else ");
			printer.indent();
			iast.getAlternant().accept(this,printer);
			printer.dedent();
		}
		printer.closeBreakParen(")");
		return null;
	}

	@Override
	public Object visit(IASTbinaryOperation iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		iast.getLeftOperand().accept(this, printer);
		printer.append(" " + iast.getOperator().getName() + " ");
		iast.getRightOperand().accept(this, printer);
		printer.closeParen(")");
		return null;
	}

	@Override
	public Object visit(IASTunaryOperation iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		printer.append(iast.getOperator().getName());	
		iast.getOperand().accept(this, printer);
		printer.closeParen(")");	
		return null;		
	}


	@Override
	public Object visit(IASTblock iast, MLPrinter printer) throws Exception {
		if (iast.getBindings().length == 0) {
			iast.getBody().accept(this,printer);			
		}
		else {
			printer.openParen("(");
			boolean first = true;
			for (IASTblock.IASTbinding x : iast.getBindings()) {
				printer.insertBreak();
				if (first) printer.append("let ");	else printer.append("and ");
				first = false;
				x.getVariable().accept(this,printer);
				IASTexpression e = x.getInitialisation();
				printer.append(" = ");
				printer.indent();
				e.accept(this, printer);
				printer.dedent();
			}
			printer.append(" in");
			printer.insertBreak();
			iast.getBody().accept(this,printer);
			printer.closeParen(")");
		}
		return null;
	}

	@Override
	public Object visit(IASTboolean iast, MLPrinter printer) throws Exception {
		printer.append(iast.getDescription());
		return null;
	}

	@Override
	public Object visit(IASTfloat iast, MLPrinter printer) throws Exception {
		printer.append(iast.getDescription());
		return null;
	}
	
	@Override
	public Object visit(IASTinteger iast, MLPrinter printer) throws Exception {
		printer.append(iast.getDescription());
		return null;
	}

	@Override
	public Object visit(IASTinvocation iast, MLPrinter printer) throws Exception {
		iast.getFunction().accept(this, printer);
		printer.openParen("(");
		appendExpressions(iast.getArguments(), ", ", printer);
		printer.closeParen(")");
		return null;
	}

	@Override
	public Object visit(IASTsequence iast, MLPrinter printer) throws Exception {
		IASTexpression[] e = iast.getExpressions();
		if (e.length != 1) printer.openParen("(");
		appendExpressions(e, "; ", printer);
		if (e.length != 1) printer.closeBreakParen(")");
		return null;
	}

	@Override
	public Object visit(IASTstring iast, MLPrinter printer) throws Exception {
		String s = iast.getDescription();
		printer.append("\"");
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '\n': printer.append("\\n"); break;
			case '\r': printer.append("\\r"); break;
			case '\t': printer.append("\\t"); break;
			case '"': printer.append("\\\""); break;
			default: printer.append(s.charAt(i) + "");
			}
		}
		printer.append("\"");
		return null;
	}
	
	@Override
	public Object visit(IASTvariable iast, MLPrinter printer) throws Exception {
		printer.append(iast.getMangledName());
		return null;
	}

}
