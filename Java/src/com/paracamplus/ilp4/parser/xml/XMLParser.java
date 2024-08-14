/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.parser.xml;


import java.io.StringReader;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTfactory;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.parser.ParseException;
import com.thaiopensource.validate.ValidationDriver;

public class XMLParser extends com.paracamplus.ilp3.parser.xml.XMLParser{

	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("classDefinition", XMLParser.class);
        addMethod("instantiation", XMLParser.class);
        addMethod("fieldRead", XMLParser.class);
        addMethod("fieldWrite", XMLParser.class);
        addMethod("send", XMLParser.class);
        addMethod("self", XMLParser.class);
        addMethod("superInvocation", XMLParser.class, "super");
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
        final List<IASTclassDefinition> classDefinitions = new Vector<>();
        final List<IASTexpression> expressions = new Vector<>();
        for ( IAST iast : iasts ) {
            if ( iast != null && iast instanceof IASTfunctionDefinition ) {
                functionDefinitions.add((IASTfunctionDefinition) iast);
            } else if ( iast != null && iast instanceof IASTclassDefinition ) {
                classDefinitions.add((IASTclassDefinition) iast);
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
        IASTclassDefinition[] clazzes =
                classDefinitions.toArray(new IASTclassDefinition[0]);
        IASTexpression[] exprs = 
            expressions.toArray(new IASTexpression[0]);
        IASTexpression body = getFactory().newSequence(exprs);
        return ((IASTfactory) getFactory()).newProgram(defs, clazzes, body);
    }


    
    public IASTclassDefinition classDefinition (Element e) 
            throws ParseException {
        String className = e.getAttribute("name");
        String superClassName = e.getAttribute("parent");

        try {
            final XPathExpression fieldPath =
                xPath.compile("./fields/field");
            final NodeList nlFields = (NodeList)
                fieldPath.evaluate(e, XPathConstants.NODESET);
            final List<String> fieldNames = new Vector<>();
            for ( int i=0 ; i<nlFields.getLength() ; i++ ) {
                final Element n = (Element) nlFields.item(i);
                fieldNames.add(n.getAttribute("name"));
            }

            final XPathExpression methodPath =
                xPath.compile("./methods/method");
            final NodeList nlMethods = (NodeList)
                methodPath.evaluate(e, XPathConstants.NODESET);
            final List<IASTmethodDefinition> methodDefinitions = new Vector<>();
            for ( int i=0 ; i<nlMethods.getLength() ; i++ ) {
                final Element method = (Element) nlMethods.item(i);
                final IASTmethodDefinition m = 
                        methodDefinition(method, className);
                methodDefinitions.add(m);
            }
            return ((IASTfactory) getFactory()).newClassDefinition(
                    className, 
                    superClassName, 
                    fieldNames.toArray(new String[0]), 
                    methodDefinitions.toArray(new IASTmethodDefinition[0]));
        } catch (XPathExpressionException e1) {
            throw new ParseException(e1);
        }
    }
    private static final XPath xPath = XPathFactory.newInstance().newXPath();
    
    public IASTmethodDefinition methodDefinition (
            Element e, 
            String definingClassName)
            throws ParseException {
        String name = e.getAttribute("name");
        IASTvariable methodVariable = getFactory().newVariable(name);
        List<IASTvariable> vs = new Vector<>();
        vs.add(((IASTfactory) getFactory()).newSelf());
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
        return ((IASTfactory) getFactory()).newMethodDefinition(
                methodVariable, variables, body, 
                name, definingClassName);
    }
    
    public IASTexpression instantiation (Element e)
            throws ParseException {
        String className = e.getAttribute("class");
        final IAST[] iasts = parseAll(e.getChildNodes());
        List<IASTexpression> exprs = new Vector<>();
        for ( IAST iast : iasts ) {
            if ( iast != null && iast instanceof IASTexpression ) {
                exprs.add((IASTexpression) iast);
            }
        }
        IASTexpression[] arguments = exprs.toArray(new IASTexpression[0]);
        return ((IASTfactory) getFactory()).newInstantiation(className, arguments);
    }
    
    public IASTexpression fieldRead (Element e)
            throws ParseException {
        String fieldName = e.getAttribute("field");
        IASTexpression target = narrowToIASTexpression(
                findThenParseChildContent(e, "target"));
        return ((IASTfactory) getFactory()).newReadField(fieldName, target);
    }
    
    public IASTexpression fieldWrite (Element e)
            throws ParseException {
        String fieldName = e.getAttribute("field");
        IASTexpression target = narrowToIASTexpression(
                findThenParseChildContent(e, "target"));
        IASTexpression value = narrowToIASTexpression(
                findThenParseChildContent(e, "value"));
        return ((IASTfactory) getFactory()).newWriteField(fieldName, target, value);
    
    }
    
    public IASTexpression send (Element e)
            throws ParseException {
        String messageName = e.getAttribute("message");
        IASTexpression receiver = narrowToIASTexpression(
                findThenParseChildContent(e, "receiver"));
        final IASTexpression[] arguments = findThenParseChildAsExpressions(e, "arguments");
        return ((IASTfactory) getFactory()).newSend(messageName, receiver, arguments);
    }
    
    public IASTvariable self (Element e)
            throws ParseException {
        return ((IASTfactory) getFactory()).newSelf();
    }
    
    public IASTexpression superInvocation (Element e)
            throws ParseException {
        return ((IASTfactory) getFactory()).newSuper();
    }
}
