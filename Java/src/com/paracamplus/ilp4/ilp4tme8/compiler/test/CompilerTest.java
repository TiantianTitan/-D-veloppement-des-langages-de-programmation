/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ilp4tme8.compiler.test;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.GlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.OperatorEnvironment;
import com.paracamplus.ilp1.compiler.OperatorStuff;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.optimizer.IdentityOptimizer;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp3.compiler.GlobalVariableStuff;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTfactoryTME8;
import com.paracamplus.ilp4.ilp4tme8.compiler.CompilerTME8;
import com.paracamplus.ilp4.compiler.test.CompilerRunner;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTfactoryTME8;
import com.paracamplus.ilp4.ilp4tme8.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp4.parser.xml.XMLParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CompilerTest extends com.paracamplus.ilp4.compiler.test.CompilerTest {
    
    protected static String[] samplesDirName = { "SamplesTME8", "SamplesTME8_2" };
    protected static String scriptCommand = "Java/src/com/paracamplus/ilp4/ilp4tme8/C/compileThenRun.sh +gc";
    
    public CompilerTest(final File file) {
    	super(file);
    }    

    public void configureRunner(CompilerRunner run) throws CompilationException {
    	// configuration du parseur
        IASTfactoryTME8 factory = new ASTfactoryTME8();
        IXMLParser xmlparser = new XMLParser(factory);
        xmlparser.setGrammar(new File(XMLgrammarFile));
        run.setXMLParser(xmlparser);
        run.setILPMLParser(new ILPMLParser(factory));
    	
        // configuration du compilateur
        IOperatorEnvironment ioe = new OperatorEnvironment();
        OperatorStuff.fillUnaryOperators(ioe);
        OperatorStuff.fillBinaryOperators(ioe);
        IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
        GlobalVariableStuff.fillGlobalVariables(gve);
        CompilerTME8 compiler = new CompilerTME8(ioe, gve);
        compiler.setOptimizer(new IdentityOptimizer());
        run.setCompiler(compiler);

        // configuration du script de compilation et exécution
        run.setRuntimeScript(scriptCommand);    	
    }
    
    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
    	return CompilerRunner.getFileList(samplesDirName, pattern);
    }    	
    
    @Test
    public void processFile() throws CompilationException, ParseException, IOException {
    	CompilerRunner run = new CompilerRunner();
    	configureRunner(run);
        run.checkPrintingAndResult(file, run.compileAndRun(file));	
    }
}
