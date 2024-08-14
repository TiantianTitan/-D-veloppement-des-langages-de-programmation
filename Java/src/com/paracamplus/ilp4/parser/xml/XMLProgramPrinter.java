package com.paracamplus.ilp4.parser.xml;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.tools.XMLPrinter;
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

public class XMLProgramPrinter
extends com.paracamplus.ilp3.parser.xml.XMLProgramPrinter
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
		for (IASTclassDefinition d : iast.getClassDefinitions()) {
			visit(d,printer);
		}
		iast.getBody().accept(this,printer);
		printer.closeTag();
		return null;
	}

	public Object visit(IASTclassDefinition iast, XMLPrinter printer) throws Exception {
		if (iast.getSuperClassName() == null) {
			printer.openTag("classDefinition", "name", iast.getName());
		}
		else {
			printer.openTag("classDefinition", "name", iast.getName(), "parent", iast.getSuperClassName());
		}
		printer.openTag("fields");
		for (String x : iast.getProperFieldNames()) {
			printer.clopenTag("field", "name", x);
		}
		printer.closeTag();		
		printer.openTag("methods");
		for (IASTmethodDefinition x : iast.getProperMethodDefinitions()) {
			printer.openTag("method", "name", x.getMethodName());
			printer.openTag("variables");
			for (IASTvariable v : x.getVariables()) {
				/*
				 * Le parseur XML ajoute un argument "self" explicite à chaque
				 * méthode.
				 * On l'enlève à l'affichage.
				 */
				if (!v.getName().equals("self")) {
					v.accept(this, printer);
				}
			}
			printer.closeTag();		
			printer.openTag("body");
			x.getBody().accept(this,printer);
			printer.closeTag();					
			printer.closeTag();
		}
		printer.closeTag();		
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTinstantiation iast, XMLPrinter printer) throws Exception {
		printer.openTag("instantiation", "class", iast.getClassName());
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this,printer);
		}
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTfieldRead iast, XMLPrinter printer) throws Exception {
		printer.openTag("fieldRead",  "field", iast.getFieldName());
		printer.openTag("target");
		iast.getTarget().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTself iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("self");
		return null;
	}

	@Override
	public Object visit(IASTsend iast, XMLPrinter printer) throws Exception {
		printer.openTag("send", "message", iast.getMethodName());
		printer.openTag("receiver");
		iast.getReceiver().accept(this, printer);
		printer.closeTag();
		printer.openTag("arguments");
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this,printer);
		}
		printer.closeTag();		
		printer.closeTag();
		return null;
	}

	@Override
	public Object visit(IASTsuper iast, XMLPrinter printer) throws Exception {
		printer.clopenTag("super");
		return null;
	}

	@Override
	public Object visit(IASTfieldWrite iast, XMLPrinter printer) throws Exception {
		printer.openTag("fieldWrite",  "field", iast.getFieldName());
		printer.openTag("target");
		iast.getTarget().accept(this,printer);
		printer.closeTag();
		printer.openTag("value");
		iast.getValue().accept(this,printer);
		printer.closeTag();
		printer.closeTag();
		return null;
	}

}
