package com.paracamplus.ilp2.parser.xml;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.tools.XMLPrinter;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class XMLProgramPrinter 
extends com.paracamplus.ilp1.parser.xml.XMLProgramPrinter
implements IASTvisitor<Object,XMLPrinter,Exception> {

	@Override
	public String getString() throws Exception {
		XMLPrinter printer = new XMLPrinter();
		visit((IASTprogram)input,printer);
		return printer.getContents();
	}
   
	public Object visit(IASTprogram iast, XMLPrinter printer) throws Exception {
		printer.openTag("program");
		for (IASTfunctionDefinition d : iast.getFunctionDefinitions()) {
			visit(d,printer);
		}
		iast.getBody().accept(this,printer);
		printer.closeTag();
		return null;
	}

	public Object visit(IASTfunctionDefinition iast, XMLPrinter printer) throws Exception {
		printer.openTag("functionDefinition", "name", iast.getName());
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
	public Object visit(IASTassignment iast, XMLPrinter printer) throws Exception {
		printer.openTag("assignment", "name", iast.getVariable().getName());
		printer.openTag("value");
		iast.getExpression().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTloop iast, XMLPrinter printer) throws Exception {
		printer.openTag("loop");
		printer.openTag("condition");
		iast.getCondition().accept(this,printer);
		printer.closeTag();				
		printer.openTag("body");
		iast.getBody().accept(this,printer);
		printer.closeTag();				
		printer.closeTag();						
		return null;
	}
	
}
