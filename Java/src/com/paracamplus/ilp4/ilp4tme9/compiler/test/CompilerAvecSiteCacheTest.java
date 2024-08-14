/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ilp4tme9.compiler.test;

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
import com.paracamplus.ilp4.ast.ASTfactory;
import com.paracamplus.ilp4.ilp4tme9.compiler.CompilerAvecSiteCache;
import com.paracamplus.ilp4.compiler.test.CompilerRunner;
import com.paracamplus.ilp4.interfaces.IASTfactory;
import com.paracamplus.ilp4.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp4.parser.xml.XMLParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CompilerAvecSiteCacheTest extends com.paracamplus.ilp4.compiler.test.CompilerTest {

    protected static String[] samplesDirName = { "SamplesTME9" };
    protected static String pattern = ".*\\.ilpml";
    protected static String scriptCommand = "Java/src/com/paracamplus/ilp4/ilp4tme9/C/compileThenRun.sh +gc";
    protected static String XMLgrammarFile = "XMLGrammars/grammar4.rng";

    public CompilerAvecSiteCacheTest(final File file) {
        super(file);
    }

    public void configureRunner(CompilerRunner run) throws CompilationException {
        // configuration du parseur
        IASTfactory factory = new ASTfactory();
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
        CompilerAvecSiteCache compiler = new CompilerAvecSiteCache(ioe, gve);
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
