package com.paracamplus.ilp1.parser;

import java.io.File;

import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.tools.Input;
import com.paracamplus.ilp1.tools.InputFromFile;

/*
 * Dispatch vers le bon parseur, en fonction de l'extension de fichier.
 */
public class Parser {

	public Parser() {		
	}

    protected IXMLParser xmlparser;
    public void setXMLParser (IXMLParser xMLParser) {
        this.xmlparser = xMLParser;
    }

    protected ILPMLParser ilpmlparser;
    public void setILPMLParser (ILPMLParser parser) {
        this.ilpmlparser = parser;
    }
        
    public IASTprogram parse(File file) throws ParseException {
		Input input = new InputFromFile(file);
    	if (file.getName().endsWith(".xml")) {
    		if (xmlparser == null) {
    			throw new ParseException("XML parser not set");
    		}
    		xmlparser.setInput(input);
    		IASTprogram program = xmlparser.getProgram();
    		return program;
    	}
    	if (file.getName().endsWith(".ilpml")) {
    		if (ilpmlparser == null) {
    			throw new ParseException("ILPML parser not set");
    		}
            ilpmlparser.setInput(input);
            IASTprogram program = ilpmlparser.getProgram();
    		return program;
    	}
    	throw new ParseException("file extension not recognized");
    }

}
