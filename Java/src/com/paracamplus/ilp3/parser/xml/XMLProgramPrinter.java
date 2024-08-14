package com.paracamplus.ilp3.parser.xml;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.tools.XMLPrinter;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTtry;
import com.paracamplus.ilp3.interfaces.IASTvisitor;

public class XMLProgramPrinter 
extends com.paracamplus.ilp2.parser.xml.XMLProgramPrinter
implements IASTvisitor<Object,XMLPrinter,Exception> {

	@Override
	public String getString() throws Exception {
		XMLPrinter printer = new XMLPrinter();
		visit((IASTprogram)input,printer);
		return printer.getContents();
	}
   
	@Override
	public Object visit(IASTcodefinitions iast, XMLPrinter printer) throws Exception {
		printer.openTag("codefinitions");
		printer.openTag("functions");
		for (IASTnamedLambda f : iast.getFunctions()) {
			// Cas spécial, il n'y a pas d'iIASTvisitor pour IASTnamedLambda
			visit(f,printer);
		}
		printer.closeTag();
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	public Object visit(IASTnamedLambda iast, XMLPrinter printer) throws Exception {
		printer.openTag("functionDefinition", "name", iast.getFunctionVariable().getName());
		printer.openTag("variables");
		for (IASTvariable x : iast.getVariables()) {
			x.accept(this, printer);
		}
		printer.closeTag();		
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();		
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTlambda iast, XMLPrinter printer) throws Exception {
		printer.openTag("lambda");
		printer.openTag("variables");
		for (IASTvariable x : iast.getVariables()) {
			x.accept(this, printer);
		}
		printer.closeTag();		
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();		
		printer.closeTag();				
		return null;
	}

	@Override
	public Object visit(IASTtry iast, XMLPrinter printer) throws Exception {
		printer.openTag("try");
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();
		IASTlambda c = iast.getCatcher();
		if (c != null) {
			// Le catcher est une lambda-expression à une seule variable
			assert(c.getVariables().length == 1);
			String e = c.getVariables()[0].getName();
			printer.openTag("catch", "exception", e);
			c.getBody().accept(this,printer);
			printer.closeTag();			
		}
		if (iast.getFinallyer() != null) {
			printer.openTag("finally");
			iast.getFinallyer().accept(this,printer);
			printer.closeTag();			
		}
		printer.closeTag();	
		return null;
	}

}
