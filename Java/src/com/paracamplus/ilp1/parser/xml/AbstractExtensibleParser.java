/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.parser.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.parser.ParseException;

public abstract class AbstractExtensibleParser extends AbstractParser {

	public AbstractExtensibleParser(IASTfactory factory) {
		super(factory);
        this.parsers = new HashMap<>();
	}
    private final HashMap<String, Method> parsers;

    public void addParser (String name, Method method) {
        this.parsers.put(name, method);
    }

    public void addMethod (String name, Class<?> clazz) {
        addParser(name, findMethod(name, clazz));
    }
    public void addMethod (String name, Class<?> clazz, String tagName) {
        addParser(tagName, findMethod(name, clazz));
    }
    
    public Method findMethod (String name, Class<?> clazz) {
        try {
          for ( Method m : clazz.getMethods() ) {
              if ( ! name.equals(m.getName()) ) {
                  continue;
              }
              if ( Modifier.isStatic(m.getModifiers()) ) {
                  continue;
              }
              Class<?>[] parameterTypes = m.getParameterTypes();
              if ( parameterTypes.length != 1 ) {
                  continue;
              }
              if ( ! Element.class.isAssignableFrom(parameterTypes[0]) ) {
                  continue;
              }
              return m;
          }
          if ( Object.class == clazz ) {
              final String msg = "Cannot find suitable parsing method for " + name;
              throw new RuntimeException(msg);
          } else {
              return findMethod(name, clazz.getSuperclass());
          }
        } catch (SecurityException e1) {
          final String msg = "Cannot access parsing method!";
          throw new RuntimeException(msg);
        }
    }
    
    @Override
	public IAST parse (final Node n) throws ParseException {
      switch ( n.getNodeType() ) {
      case Node.ELEMENT_NODE: {
        final Element e = (Element) n;
        final String name = e.getTagName();
       if ( parsers.containsKey(name) ) {
            final Method method = parsers.get(name);
            try {
              Object result = method.invoke(this, new Object[]{e});
              if ( result != null && result instanceof IAST ) {
            	  return (IAST) result;
              } else {
            	  final String msg = "Not an IAST " + result;
            	  throw new ParseException(msg);
              }
            } catch (IllegalArgumentException exc) {
              throw new ParseException(exc);
            } catch (IllegalAccessException exc) {
              throw new ParseException(exc);
            } catch (InvocationTargetException exc) {
                Throwable t = exc.getTargetException();
                if ( t instanceof ParseException ) {
                    throw (ParseException) t;
                } else {
                    throw new ParseException(exc);
                }
            }

        } else {
          final String msg = "Unknown element name: " + name;
          throw new ParseException(msg);
        }
      }

      default: {
        final String msg = "Unknown node type: " + n.getNodeName();
        throw new ParseException(msg);
      }
      }
    }
}
