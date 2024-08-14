/* *****************************************************************
 * ilp1 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp1
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.compiler.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.Compiler;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.parser.Parser;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.tools.FileTool;
import com.paracamplus.ilp1.tools.ProgramCaller;

// classe facilitant la compilation et l'exécution de programmes
public class CompilerRunner {

	protected Parser parser;
	
    public CompilerRunner() {
    	parser = new Parser();
    }

    public void setXMLParser (IXMLParser p) {
    	parser.setXMLParser(p);
    }

    public void setILPMLParser(ILPMLParser p) {
    	parser.setILPMLParser(p);
    }

    protected Compiler compiler;
    public void setCompiler(Compiler compiler)  {
	   this.compiler = compiler;
   }

    protected String runtimeScript;
    public void setRuntimeScript(String runtimeScript) {
    	this.runtimeScript = runtimeScript;
    }
    
    
    public String compileAndRun(File file) 
    		throws ParseException, CompilationException, IOException {
        System.err.println("Testing " + file.getAbsolutePath() + " ...");
        assertTrue(file.exists());

        // lancement du parsing
        IASTprogram program = parser.parse(file);
    	
        // lancement de la compilation vers C
        if (compiler == null) {
        	throw new CompilationException("no compiler set");
        }
        String compiled = compiler.compile(program);
        File cFile = FileTool.changeSuffix(file, "c");
        FileTool.stuffFile(cFile, compiled);

        // detection de Windows
        boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0;

        String cPath =  cFile.getAbsolutePath();
        if (isWindows) {
        	// sous Windows, adaptation du chemin du fichier
        	cPath = "\"/mnt/" + cPath.substring(0,1).toLowerCase() + cPath.replace('\\', '/').substring(2) + "\"";
        }

        // pretty-print du C généré et affichage
        try {
            String indentProgram = "indent " + cPath;
            ProgramCaller pcindent = new ProgramCaller(indentProgram);
            pcindent.run();
        } catch (Exception exc) {
        }
        System.out.println(FileTool.slurpFile(cFile));

        // lancement du script de compilation et d'exécution
        if (runtimeScript == null) {
        	throw new CompilationException("runtime script not set");
        }
        String compileProgram = "bash " + runtimeScript + " " + cPath;
        ProgramCaller pc = new ProgramCaller(compileProgram);
        pc.setVerbose();
        pc.run();
        assertEquals("Comparing return code", 0, pc.getExitValue());
        return pc.getStdout().trim();	
    }

    public void checkPrintingAndResult(File file, String actual) throws IOException {
        String expectedPrinting = readExpectedPrinting(file);
        String expectedResult = readExpectedResult(file);
        String expected = expectedPrinting + expectedResult;
        assertEquals("Compare", expected, actual);
    }
    
    public static Collection<File[]> getFileList(
    		String[] samplesDirNames,
    		String pattern
    		) throws Exception {
    	Collection<File[]> result = new Vector<>();
    	File[] testFiles = FileTool.getFileList(samplesDirNames, pattern);
    	for ( final File f : testFiles ) {
    		result.add(new File[]{ f });
    	}
        return result;
    }
       
    public String readExpectedPrinting (File file)
      throws IOException {
      File resultFile = FileTool.changeSuffix(file, "print");
      assertTrue(file.exists());
      return FileTool.slurpFile(resultFile).trim();
    }

    public String readExpectedResult (File file)
      throws IOException {
      File resultFile = FileTool.changeSuffix(file, "result");
      assertTrue(file.exists());
      return FileTool.slurpFile(resultFile).trim();
    }

}
