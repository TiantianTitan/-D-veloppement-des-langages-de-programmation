package com.paracamplus.ilp4.parser.converter;

import java.io.File;

import com.paracamplus.ilp4.parser.ilpml.ILPMLProgramPrinter;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.tools.FileTool;
import com.paracamplus.ilp1.tools.Input;
import com.paracamplus.ilp1.tools.InputFromFile;
import com.paracamplus.ilp4.ast.ASTfactory;
import com.paracamplus.ilp4.interfaces.IASTfactory;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.parser.xml.XMLParser;

/*
 * Conversion entre des programmes XML et des programmes ILPML.
 */
public class ConvertXMLtoILPML {

    protected static String grammarFile = "XMLGrammars/grammar4.rng";

	public static void convertXMLtoILPML(File source, File destination) throws Exception {
		IASTfactory factory = new ASTfactory();
		IXMLParser xMLParser = new XMLParser(factory);		
        Input input = new InputFromFile(source);
        xMLParser.setGrammar(new File(grammarFile));
        xMLParser.setInput(input);
        IASTprogram program = (IASTprogram) xMLParser.getProgram();
		
		ILPMLProgramPrinter printer = new ILPMLProgramPrinter();
		printer.setInput(program);
		String output = printer.getString();
		FileTool.stuffFile(destination, output);        
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			throw new IllegalArgumentException("Spécifiez un répertoire de fichiers à convertir");
		}
		File[] files = FileTool.getFileList(args[0], ".*\\.xml");
		for (File file : files) {
			File dest = FileTool.changeSuffix(file, "ilpml");
			System.out.println("Converting " + file + " into " + dest);
			convertXMLtoILPML(file, dest);
		}
	}

}
