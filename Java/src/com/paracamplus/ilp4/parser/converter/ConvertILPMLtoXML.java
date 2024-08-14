package com.paracamplus.ilp4.parser.converter;

import java.io.File;

import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.XMLProgramPrinter;
import com.paracamplus.ilp1.tools.FileTool;
import com.paracamplus.ilp1.tools.Input;
import com.paracamplus.ilp1.tools.InputFromFile;
import com.paracamplus.ilp1.ast.ASTfactory;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTprogram;

/*
 * Conversion entre des programmes XML et des programmes ILPML.
 */
public class ConvertILPMLtoXML {

	public static void convertILPMLtoXML(File source, File destination) throws Exception {
		IASTfactory factory = new ASTfactory();
		ILPMLParser parser = new ILPMLParser(factory);		
        Input input = new InputFromFile(source);
        parser.setInput(input);
        IASTprogram program = (IASTprogram) parser.getProgram();
		
		XMLProgramPrinter printer = new XMLProgramPrinter();
		printer.setInput(program);
		String output = printer.getString();
		FileTool.stuffFile(destination, output);        
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			throw new IllegalArgumentException("Spécifiez un répertoire de fichiers à convertir");
		}
		File[] files = FileTool.getFileList(args[0], ".*\\.ilpml");
		for (File file : files) {
			File dest = FileTool.changeSuffix(file, "xml");
			System.out.println("Converting " + file + " into " + dest);
			convertILPMLtoXML(file, dest);
		}
	}

}
