/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ilp4tme10.compiler.test;

import java.io.File;
import java.util.Collection;

import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp4.parser.xml.XMLParser;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.GlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.OperatorEnvironment;
import com.paracamplus.ilp1.compiler.OperatorStuff;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.optimizer.IdentityOptimizer;
import com.paracamplus.ilp3.compiler.GlobalVariableStuff;
import com.paracamplus.ilp4.compiler.test.CompilerRunner;
import com.paracamplus.ilp4.ilp4tme10.ast.ASTfactoryTME10;
import com.paracamplus.ilp4.ilp4tme10.compiler.CompilerTME10;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTfactoryTME10;
import com.paracamplus.ilp4.ilp4tme10.parser.ilpml.ILPMLParserTME10;

@RunWith(Parameterized.class)
public class CompilerTest extends com.paracamplus.ilp4.compiler.test.CompilerTest {

    protected static String[] samplesDirName = { "SamplesTME10" /*, "SamplesILP4", "SamplesILP3", "SamplesILP2", "SamplesILP1"*/ };
  
	public CompilerTest(final File file) {
		super(file);
	}    

  public void configureRunner(CompilerRunner run) throws CompilationException {
	  super.configureRunner(run);
  	// configuration du parseur
      IASTfactoryTME10 factory = new ASTfactoryTME10();
        IXMLParser xmlparser = new XMLParser(factory);
        xmlparser.setGrammar(new File(XMLgrammarFile));
        run.setXMLParser(xmlparser);
        run.setILPMLParser(new ILPMLParserTME10(factory));

      // configuration du compilateur
      IOperatorEnvironment ioe = new OperatorEnvironment();
      OperatorStuff.fillUnaryOperators(ioe);
      OperatorStuff.fillBinaryOperators(ioe);
      IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
      GlobalVariableStuff.fillGlobalVariables(gve);
      CompilerTME10 compiler = new CompilerTME10(ioe, gve);
      compiler.setOptimizer(new IdentityOptimizer());
      run.setCompiler(compiler);
  }
  
  @Parameters(name = "{0}")
  public static Collection<File[]> data() throws Exception {
  	return CompilerRunner.getFileList(samplesDirName, pattern);
  }
  
}