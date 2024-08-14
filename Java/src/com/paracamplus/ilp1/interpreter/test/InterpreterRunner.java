/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Vector;

import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interpreter.EmptyLexicalEnvironment;
import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.parser.Parser;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.tools.FileTool;

//classe facilitant l'interprétation de programmes
public class InterpreterRunner {
	
	protected Parser parser;
    
    public InterpreterRunner() {
    	stdout = new BufferedWriter(new OutputStreamWriter(System.out));
    	parser = new Parser();
    }

    public void setXMLParser (IXMLParser p) {
    	parser.setXMLParser(p);
    }

    public void setILPMLParser (ILPMLParser p) {
    	parser.setILPMLParser(p);
    }
    
    protected Interpreter interpreter;
    public void setInterpreter(Interpreter interpreter)  {
    	this.interpreter = interpreter;
   }

    protected Writer stdout;
    public void setStdout(Writer stdout) {
    	this.stdout = stdout;
    }
    
    public void interpretProgram(IASTprogram program) throws EvaluationException {
    	if (interpreter == null) {
    		throw new EvaluationException("interpreter not set");
    	}
        ILexicalEnvironment lexenv = new EmptyLexicalEnvironment();
        result = interpreter.visit(program, lexenv);
        printing = stdout.toString();
        System.out.println("  Value: " + result);
        if ( ! "".equals(printing) ) {
            System.out.println("  Printing: " + printing);
        }    	
    }
    
    public void testFile(File file) 
    		throws ParseException, IOException, EvaluationException {
        System.err.println("Testing " + file.getAbsolutePath() + " ...");
        assertTrue(file.exists());
        IASTprogram program = parser.parse(file);
        interpretProgram(program);
    }
    
    protected String printing;
    public String getPrinting() {
    	return printing;
    }
    
    protected Object result;
    public Object getResult() {
    	return result;
    }

    private static Object normalizeResult(Object value) {
        if (value instanceof BigInteger) {
            return ((BigInteger)value).intValue();
        } else if ( value instanceof BigDecimal ) {
            return ((BigDecimal)value).doubleValue();
        } else {
            return value;
        }
    }
    
    public void checkResult (File file) throws IOException {
        String expectedResult = readExpectedResult(file);
        Object value = normalizeResult(result);
        if ( value instanceof Double ) {
            double expected = Double.parseDouble(expectedResult);
            assertEquals("Comparing double results", 
                    expected,
                    (double)value, 
                    0.01);
        } else if ( value instanceof Integer ) {
            assertEquals("Comparing integer results",
                    expectedResult.toString(),
                    value.toString());
        } else {
            assertEquals("Comparing results", 
                    expectedResult,
                    value.toString());
        }
    }
    
    public void checkPrinting(File file) throws IOException {
        String expectedPrinting = readExpectedPrinting(file);
        assertEquals("Comparing printings", expectedPrinting, printing);
    }
    
    public void checkPrintingAndResult(File file) throws IOException {
    	checkPrinting(file);
    	checkResult(file);
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
