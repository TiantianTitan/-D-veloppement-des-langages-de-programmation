/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.parser.xml;

import java.io.File;
import java.io.StringReader;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.tools.Input;
import com.thaiopensource.validate.ValidationDriver;

public class XMLParser extends AbstractExtensibleParser {

	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("alternative", XMLParser.class);
        addMethod("sequence", XMLParser.class);
        addMethod("integerConstant", XMLParser.class, "integer");
        addMethod("floatConstant", XMLParser.class, "float");
        addMethod("stringConstant", XMLParser.class, "string");
        addMethod("booleanConstant", XMLParser.class, "boolean");
        addMethod("unaryOperation", XMLParser.class);
        addMethod("binaryOperation", XMLParser.class);
        addMethod("block", XMLParser.class);
        addMethod("binding", XMLParser.class);
        addMethod("variable", XMLParser.class);
        addMethod("invocation", XMLParser.class);
	}
	  
    @Override
	public void setInput(Input input) {
        this.input = input;
    }
    protected Input input;
    
    @Override
	public void setGrammar (File rngFile) {
        this.rngFile = rngFile;
    }
    protected File rngFile;
    
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
    
    public static IASTexpression narrowToIASTexpression (IAST iast) 
            throws ParseException {
        if ( iast != null && iast instanceof IASTexpression ) {
            return (IASTexpression) iast;
        } else {
            final String msg = "Not an ASTexpression " + iast;
            throw new ParseException(msg);
        }
    }
    
    public static IASTvariable narrowToIASTvariable (IAST iast) 
            throws ParseException {
        if ( iast != null && iast instanceof IASTvariable) {
            return (IASTvariable) iast;
        } else {
            final String msg = "Not an ASTvariable" + iast;
            throw new ParseException(msg);
        }
    }
    
    @Override
	public IASTprogram parse (Document d) throws ParseException {
        final Element e = d.getDocumentElement();
        final IAST[] iasts = parseAll(e.getChildNodes());
        final List<IASTexpression> expressions = new Vector<>();
        for ( IAST iast : iasts ) {
            if ( iast != null && iast instanceof IASTexpression ) {
                expressions.add((IASTexpression) iast);
            } else {
                final String msg = "Should never occur!";
                assert false : msg;
                throw new ParseException(msg);
            }
        }
        IASTexpression[] exprs = 
            expressions.toArray(new IASTexpression[0]);
        IASTexpression body = getFactory().newSequence(exprs);
        return getFactory().newProgram(body);
    }
    
           

	public IASTexpression alternative (Element e) throws ParseException {
        IAST iastc = findThenParseChildContent(e, "condition");
        IASTexpression condition = narrowToIASTexpression(iastc);
        IASTexpression[] iaste = 
                findThenParseChildAsExpressions(e, "consequence");
        IASTexpression consequence = getFactory().newSequence(iaste);
        try {
            IASTexpression[] iasta = 
                    findThenParseChildAsExpressions(e, "alternant");
            IASTexpression alternant = getFactory().newSequence(iasta);
            return getFactory().newAlternative(
                    condition, consequence, alternant);
        } catch (ParseException exc) {
            return getFactory().newAlternative(
                    condition, consequence, null);
        }
    }
	
	public IASTexpression sequence (Element e) throws ParseException {
	    final IAST[] iasts = parseAll(e.getChildNodes());
	    List<IASTexpression> exprs = new Vector<>();
	    for ( IAST iast : iasts ) {
	        if ( iast != null && iast instanceof IASTexpression ) {
	            exprs.add((IASTexpression) iast);
	        }
	    }
	    IASTexpression[] expressions = exprs.toArray(new IASTexpression[0]);
	    return getFactory().newSequence(expressions);
	}
	
	public IASTexpression integerConstant (Element e) throws ParseException {
	    final String description = e.getAttribute("value");
	    return getFactory().newIntegerConstant(description);
	}

    public IASTexpression floatConstant (Element e) throws ParseException {
        final String description = e.getAttribute("value");
        return getFactory().newFloatConstant(description);
    }

    public IASTexpression stringConstant (Element e) throws ParseException {
        final String description = e.getTextContent();
        return getFactory().newStringConstant(description);
    }

    public IASTexpression booleanConstant (Element e) throws ParseException {
        final String description = e.getAttribute("value");
        return getFactory().newBooleanConstant(description);
    }
    
    public IASTexpression variable (Element e) throws ParseException {
        final String name = e.getAttribute("name");
        IASTvariable variable = getFactory().newVariable(name);
        return variable;
    }
    
    public IASTexpression unaryOperation (Element e) 
            throws ParseException {
        IAST iast = findThenParseChildContent(e, "operand");
        IASTexpression operand = narrowToIASTexpression(iast);
        String operatorName = e.getAttribute("operator");
        IASToperator operator = getFactory().newOperator(operatorName);
        return getFactory().newUnaryOperation(operator, operand);
    }

    public IASTexpression binaryOperation (Element e) 
            throws ParseException {
        IAST iast1 = findThenParseChildContent(e, "leftOperand");
        IASTexpression operand1 = narrowToIASTexpression(iast1);
        IAST iast2 = findThenParseChildContent(e, "rightOperand");
        IASTexpression operand2 = narrowToIASTexpression(iast2);
        String operatorName = e.getAttribute("operator");
        IASToperator operator = getFactory().newOperator(operatorName);
        return getFactory().newBinaryOperation(operator, operand1, operand2);
    }
    
    public IASTexpression block (Element e) throws ParseException {
        IAST[] iastbindings = findThenParseChildAsArray(e, "bindings");
        List<IASTbinding> b = new Vector<>();
        for ( IAST iastb : iastbindings ) {
            if ( iastb != null && iastb instanceof IASTbinding ) {
                b.add((IASTbinding) iastb);
            } else {
                String msg = "Not an IASTbinding " + iastb;
                throw new ParseException(msg);
            }
        }
        IASTbinding[] bindings = b.toArray(new IASTbinding[0]);
        IASTexpression[] iasts = findThenParseChildAsExpressions(e, "body");
        IASTexpression body = getFactory().newSequence(iasts);
        return getFactory().newBlock(bindings, body);
    }
    
    public IASTbinding binding (Element e) throws ParseException {
        // Casts ensured by grammar9:
        Element v = findChild(e, "variable");
        String name = v.getAttribute("name");
        IASTvariable variable = getFactory().newVariable(name);
        IASTexpression exp = narrowToIASTexpression(
                findThenParseChildContent(e, "initialisation"));
        return getFactory().newBinding(variable, exp);
    }
    
    public IASTexpression invocation (Element e) throws ParseException {
        // Casts ensured by grammar9:
    	IASTexpression fun = narrowToIASTexpression(
                findThenParseChildContent(e, "function"));
         IASTexpression[] iasts = 
                findThenParseChildAsExpressions(e, "arguments");
        return getFactory().newInvocation(fun, iasts);
    }

}
