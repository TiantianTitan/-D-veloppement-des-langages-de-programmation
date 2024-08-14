package com.paracamplus.ilp1.parser.ilpml;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr4.ILPMLgrammar1Lexer;
import antlr4.ILPMLgrammar1Parser;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.tools.Input;

public class ILPMLParser {
	
	protected IASTfactory factory;

	public ILPMLParser(IASTfactory factory) {
		this.factory = factory;
	}
	
	protected Input input;
	
	public void setInput(Input input) {
		this.input = input;
	}
	
    public IASTprogram getProgram() throws ParseException {
		try {
			ANTLRInputStream in = new ANTLRInputStream(input.getText());
			// flux de caractères -> analyseur lexical
			ILPMLgrammar1Lexer lexer = new ILPMLgrammar1Lexer(in);
			// analyseur lexical -> flux de tokens
			CommonTokenStream tokens =	new CommonTokenStream(lexer);
			// flux tokens -> analyseur syntaxique
			ILPMLgrammar1Parser parser =	new ILPMLgrammar1Parser(tokens);
			// démarage de l'analyse syntaxique
			ILPMLgrammar1Parser.ProgContext tree = parser.prog();		
			// parcours de l'arbre syntaxique et appels du Listener
			ParseTreeWalker walker = new ParseTreeWalker();
			ILPMLListener extractor = new ILPMLListener(factory);
			walker.walk(extractor, tree);	
			return tree.node;
		} catch (Exception e) {
			throw new ParseException(e);
		}
    }

}
