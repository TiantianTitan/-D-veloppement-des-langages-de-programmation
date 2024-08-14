/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp3.parser.xml;

import java.io.StringReader;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp3.interfaces.IASTfactory;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTnamedLambda;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.parser.ParseException;
import com.thaiopensource.validate.ValidationDriver;

    public class XMLParser extends com.paracamplus.ilp2.parser.xml.XMLParser  {

	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("tryInstruction", XMLParser.class, "try");
        addMethod("lambda", XMLParser.class);
        addMethod("codefinitions", XMLParser.class);
       
	}
    
    @Override
	public IASTprogram getProgram() throws ParseException {
        try {
            final String programText = input.getText();
            final String rngFilePath = rngFile.getAbsolutePath();
            final InputSource isg = ValidationDriver.fileInputSource(rngFilePath);
            final ValidationDriver vd = new ValidationDriver();
            vd.loadSchema(isg);
            
            InputSource is = new InputSource(new StringReader(programText));
            if ( ! vd.validate(is) ) {
                throw new ParseException("Invalid XML program!");
            }

            final DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            // the previous value of is is totally drained!
            is = new InputSource(new StringReader(programText));
            final Document document = db.parse(is);
            IASTprogram program = parse(document);
            return program;
        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }   

    // 
 
    
    @Override
	public IASTprogram parse (Document d) throws ParseException {
        final Element e = d.getDocumentElement();
        final IAST[] iasts = parseAll(e.getChildNodes());
        final List<IASTfunctionDefinition> functionDefinitions = new Vector<>();
        final List<IASTexpression> expressions = new Vector<>();
        for ( IAST iast : iasts ) {
            if ( iast != null && iast instanceof IASTfunctionDefinition ) {
                functionDefinitions.add((IASTfunctionDefinition) iast);
            } else if ( iast != null && iast instanceof IASTexpression ) {
                expressions.add((IASTexpression) iast);
            } else {
                final String msg = "Should never occur!";
                assert false : msg;
                throw new ParseException(msg);
            }
        }
        IASTfunctionDefinition[] defs =
            functionDefinitions.toArray(new IASTfunctionDefinition[0]);
        IASTexpression[] exprs = 
            expressions.toArray(new IASTexpression[0]);
        IASTexpression body = getFactory().newSequence(exprs);
        return ((IASTfactory) getFactory()).newProgram(defs, body);
    }

    
    public IASTexpression tryInstruction (Element e) throws ParseException {
        IASTexpression[] expressions =
                findThenParseChildAsExpressions(e, "body");
        IASTexpression body = getFactory().newSequence(expressions);
        IASTlambda catcher = null;
        try {
            Element catchElement = findChild(e, "catch");
            String name = catchElement.getAttribute("exception");
            IASTvariable variable = getFactory().newVariable(name);
            IASTvariable[] variables = new IASTvariable[]{ variable };
            IASTexpression[] cexprs = 
                    findThenParseChildAsExpressions(e, "catch");
            IASTexpression cbody = getFactory().newSequence(cexprs);
            catcher = ((IASTfactory) getFactory()).newLambda(variables, cbody);
        } catch (ParseException exc) {
            // No catch element
        }
        IASTexpression finallyer = null;
        try {
            IASTexpression[] fexprs =
                    findThenParseChildAsExpressions(e, "finally");
            finallyer = getFactory().newSequence(fexprs);
        } catch (ParseException exc) {
            // No finally element
        }
        return ((IASTfactory) getFactory()).newTry(body, catcher, finallyer);
    }
    
    public IASTlambda lambda (Element e) throws ParseException {
        List<IASTvariable> vs = new Vector<>();
        NodeList vars = findChild(e, "variables").getChildNodes();
        for ( int i=0 ; i<vars.getLength() ; i++ ) {
            Node nd = vars.item(i);
            if ( nd.getNodeType() == Node.ELEMENT_NODE ) {
                Element v = (Element) nd;
                String variableName = v.getAttribute("name");
                IASTvariable variable = getFactory().newVariable(variableName);
                vs.add(variable);
            }
        }
        IASTvariable[] variables = vs.toArray(new IASTvariable[0]);
        IASTexpression[] expressions =
                findThenParseChildAsExpressions(e, "body");
        IASTexpression body = getFactory().newSequence(expressions);
        return ((IASTfactory) getFactory()).newLambda(variables, body);
    }
    
    public IASTexpression codefinitions (Element e) throws ParseException {
        List<IASTnamedLambda> fs = new Vector<>();
        for ( IAST ifd : findThenParseChildAsArray(e, "functions")) {
            // cast ensured by grammar
            IASTfunctionDefinition fd = (IASTfunctionDefinition) ifd;
            IASTnamedLambda fun = ((IASTfactory) getFactory()).newNamedLambda(
                    fd.getFunctionVariable(),
                    fd.getVariables(),
                    fd.getBody());
            fs.add(fun);
        }
        IASTexpression[] expressions =
                findThenParseChildAsExpressions(e, "body");
        IASTexpression body = getFactory().newSequence(expressions);
        return ((IASTfactory) getFactory()).newCodefinitions(
                fs.toArray(new IASTnamedLambda[0]),
                body );
    }
    
}
