package com.paracamplus.ilp1.tools;

/*
 * Pretty-printer pour afficher un programme indenté.
 */
public class MLPrinter {

	// buffer
	protected StringBuilder buf;

	// Identation et saut à la ligne
	protected int indent;
	protected boolean lastBreak = true;
	
	public MLPrinter() {
		buf = new StringBuilder();
	}	

	public String getContents() {
		return buf.toString();
	}

	public void insertBreak() {
		if (!lastBreak) {
			buf.append("\n");
			for (int i = 0; i < indent; i++)
				buf.append("    ");
			lastBreak = true;
		}
	}
	
	public void append(String s) {
		lastBreak =false;
		buf.append(s);
	}
	
	public void openParen(String paren) {
		indent++;
		append(paren);
	}
	
	public void closeParen(String paren) {
		indent--;
		append(paren);
	}
	
	public void openBreakParen(String paren) {
		insertBreak();
		indent++;
		append(paren);
	}
	
	public void closeBreakParen(String paren) {
		indent--;
		insertBreak();
		append(paren);
	}

	public void indent() {
		indent++;
	}
	
	public void dedent() {
		indent--;
	}
}
