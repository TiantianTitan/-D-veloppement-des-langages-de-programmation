/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.parser.xml;

import java.util.List;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ParseException;

public abstract class AbstractParser implements IXMLParser {

	public AbstractParser(IASTfactory factory) {
		this.factory = factory;
	}
	@Override
	public IASTfactory getFactory() {
		return factory;
	}
	protected final IASTfactory factory;
	
    // Parsing entry points:
    
	public abstract IASTprogram parse(Document dom) throws ParseException;	
	public abstract IAST parse(Node e) throws ParseException;

	// Common utilities
	
	@Override
	public Element findChild (final Node n, final String childName)
	throws ParseException {
	    return findChild(n.getChildNodes(), childName);
	}
	
	@Override
	public Element findChild (final NodeList nl, final String childName)
    throws ParseException {
        final int n = nl.getLength();
        for ( int i = 0 ; i<n ; i++ ) {
            final Node nd = nl.item(i);
            switch ( nd.getNodeType() ) {

            case Node.ELEMENT_NODE: {
                final Element e = (Element) nd;
                if ( childName.equals(e.getTagName()) ) {
                    return e;
                }
                break;
            }

            default: {
                // Just ignore everything but XML element.
            }
            }
        }
        final String msg = "No such child element " + childName;
        throw new ParseException(msg);
	}
	
    public IAST	findThenParseChild (final Node n, final String childName)
    throws ParseException {
        return findThenParseChild(n.getChildNodes(), childName);
    }

    @Override
	public IAST	findThenParseChild (final NodeList nl, final String childName)
    throws ParseException {
        Element e = findChild(nl, childName);
        return this.parse(e);
    }

    public IAST findThenParseChildContent (final Node n, final String childName)
    throws ParseException {
        return findThenParseChildContent(n.getChildNodes(), childName);
    }

    public IAST findThenParseChildContent (final NodeList nl, final String childName)
    throws ParseException {
        IAST[] iast = findThenParseChildAsArray(nl, childName);
        if ( iast.length == 1 ) {
            return iast[0];
        } else {
            String msg = "Non single content " + nl;
            throw new ParseException(msg);
        }
    }
  
    @Override
	public IASTexpression[] findThenParseChildAsExpressions (
            final Node n, final String childName)
    throws ParseException {
        return findThenParseChildAsExpressions(n.getChildNodes(), childName);
    }

    @Override
	public IASTexpression[] findThenParseChildAsExpressions (
            final NodeList nl, final String childName)
    throws ParseException {
        IAST[] iasts = findThenParseChildAsArray(nl, childName);
        final List<IASTexpression> result = new Vector<>();
        for ( IAST iast : iasts ) {
            if ( iast instanceof IASTexpression ) {
                result.add((IASTexpression) iast);
            } else {
                String msg = "Not an IASTexpression " + iast;
                throw new ParseException(msg);
            }
        }
        return result.toArray(new IASTexpression[0]);
    }
    
    @Override
	public IAST[] findThenParseChildAsArray (
            final Node n, final String childName)
    throws ParseException {
        return findThenParseChildAsArray(n.getChildNodes(), childName);
    }

    @Override
	public IAST[] findThenParseChildAsArray (
            final NodeList nl, final String childName)
    throws ParseException {
        Element e = findChild(nl, childName);
        return this.parseAll(e.getChildNodes());
    }
 
    public IAST[] parseAll (NodeList nl)
    throws ParseException {
        final List<IAST> result = new Vector<>();
        final int n = nl.getLength();
        LOOP:
            for ( int i = 0 ; i<n ; i++ ) {
                final Node nd = nl.item(i);
                switch ( nd.getNodeType() ) {

                case Node.ELEMENT_NODE: {
                	final Element e = (Element) nd;
                    final IAST p = this.parse(e);
                    result.add(p);
                    continue LOOP;
                }

                default: {
                	// Just ignore everything but XML element.
                }
                }
            }
        return result.toArray(new IAST[0]);
    }
}
