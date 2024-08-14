package com.paracamplus.ilp1.tools;

import java.util.Stack;

/*
 * Pretty-printer pour (un sous-ensemble de) XML.
 */
public class XMLPrinter {

	/* Buffer accumulant le texte XML */
	protected StringBuilder buf;
	
	/* Pile des tags ouverts */
	protected Stack<String> tags;
	
	/* Niveau d'indentation */
	protected int indent = 0;
	
	
	public XMLPrinter() {
		buf = new StringBuilder();
		tags = new Stack<>();
		buf.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
	}
	
	public String getContents() {
		return buf.toString();
	}
	
	 public void openTag(String tag, String ... attr) {
		 tags.push(tag);
		 appendIndent();
		 buf.append("<");
		 appendEscaped(tag);
		 appendAttributes(attr);
		 buf.append(">\n");
		 indent++;
	 }
	 
	 public void closeTag() {
		 indent--;
		 appendIndent();
		 buf.append("</");
		 appendEscaped(tags.pop()); 
		 buf.append(">\n");
	 }
	
	 /* Tag auto-fermant */
	 public void clopenTag(String tag, String ... attr) {
		 appendIndent();
		 buf.append("<");
		 appendEscaped(tag);
		 appendAttributes(attr);
		 buf.append("/>\n");
	 }
	 
	 /* Tag contenant du texte */
	 public void clopenTagText(String tag, String text, String ... attr) {
		 appendIndent();
		 buf.append("<");
		 appendEscaped(tag);
		 appendAttributes(attr);
		 buf.append(">");
		 appendEscaped(text);
		 buf.append("</");
		 appendEscaped(tag);
		 buf.append(">\n");		 
	 }
	 public void text(String text) {
		 appendEscaped(text);
	 }
	 
	 /* Utilitaire pour afficher une liste d'attributs */
	 protected void appendAttributes(String[] attr) {
		 for (int i = 0; i < attr.length / 2; i++) {
			 buf.append(" ");
			 appendEscaped(attr[2*i]);
			 buf.append("='");
			 appendEscaped(attr[2*i+1]);
			 buf.append("'");
		 }
	 }
	 
	 /* Utilitaire pour protéger les caractères spéciaux. */
	 protected void appendEscaped(String s) {
		 for (int i = 0; i < s.length(); i++) {
			 switch (s.charAt(i)) {
			 case '"': buf.append("&quot;"); break;
			 case '\'': buf.append("&apos;"); break;
			 case '&': buf.append("&amp;"); break;
			 case '<': buf.append("&lt;"); break;
			 case '>': buf.append("&gt;"); break;
			 default: buf.append(s.charAt(i));
			 }
		 }			 
	 }
	
	 /* Matérialise l'indentation */
	 protected void appendIndent() {
		 for (int i = 0; i < indent; i++)
			 buf.append("    ");
	 }
	 
}
