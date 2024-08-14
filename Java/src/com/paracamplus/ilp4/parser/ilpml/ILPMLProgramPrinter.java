package com.paracamplus.ilp4.parser.ilpml;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;
import com.paracamplus.ilp1.tools.MLPrinter;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp4.interfaces.IASTvisitor;

/* 
 * Affichage d'un AST sous forme ILML, en utilisant un visiteur.
 */
public class ILPMLProgramPrinter
extends com.paracamplus.ilp3.parser.ilpml.ILPMLProgramPrinter
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
		for (IASTclassDefinition d : iast.getClassDefinitions()) {
			visit(d,printer);
		}
		iast.getBody().accept(this,printer);
		printer.insertBreak();
		return null;
	}
	
	public Object visit(IASTclassDefinition iast, MLPrinter printer) throws Exception {
		printer.insertBreak();
		printer.append("class " + iast.getMangledName());
		if (iast.getSuperClassName() != null) {
			printer.append(" extends " + iast.getSuperClassName());
		}
		printer.insertBreak();
		printer.openBreakParen("{");
		for (String x : iast.getProperFieldNames()) {
			printer.insertBreak();
			printer.append("var " + Inamed.computeMangledName(x) + ";");
		}
		for (IASTmethodDefinition x : iast.getProperMethodDefinitions()) {
			printer.insertBreak();
			printer.append("method " +  x.getMangledName() + " (");
			boolean first = true;
			for (IASTvariable v : x.getVariables()) {
				// Le parseur XML ajout un argument "self" explicite à chaque méthode.
	            // On l'enlève à l'affichage.
				if (!v.getName().equals("self")) {
					if (first) first = false; else printer.append(",");
					printer.append(v.getMangledName());
				}
			}
			printer.append(")");
			printer.insertBreak();
			x.getBody().accept(this,printer);
			printer.append(";");
		}
		printer.closeBreakParen("}");
		return null;
	}

	
	// Implantation des méthodes du visiteur IASTvisitable

	
	@Override
	public Object visit(IASTinstantiation iast, MLPrinter printer) throws Exception {
		printer.append("new " +  Inamed.computeMangledName(iast.getClassName()));
		printer.openParen("(");
		appendExpressions(iast.getArguments(), ", ", printer);
		printer.closeParen(")");
		return null;
	}

	@Override
	public Object visit(IASTfieldRead iast, MLPrinter printer) throws Exception {
		iast.getTarget().accept(this,printer);
		printer.append("." + Inamed.computeMangledName(iast.getFieldName()));
		return null;
	}

	@Override
	public Object visit(IASTself iast, MLPrinter printer) throws Exception {
		printer.append("self");
		return null;
	}

	@Override
	public Object visit(IASTsend iast, MLPrinter printer) throws Exception {
		iast.getReceiver().accept(this, printer);		
		printer.append("." +  Inamed.computeMangledName(iast.getMethodName()));
		printer.openParen("(");
		appendExpressions(iast.getArguments(), ", ", printer);
		printer.closeParen(")");
		return null;
	}
	
	@Override
	public Object visit(IASTsuper iast, MLPrinter printer) throws Exception {
		printer.append("super");
		return null;
	}

	@Override
	public Object visit(IASTfieldWrite iast, MLPrinter printer) throws Exception {
		printer.openParen("(");
		iast.getTarget().accept(this,printer);
		printer.append("." + Inamed.computeMangledName(iast.getFieldName()) + " = ");
		iast.getValue().accept(this,printer);
		printer.closeParen(")");	
		return null;
	}

}
