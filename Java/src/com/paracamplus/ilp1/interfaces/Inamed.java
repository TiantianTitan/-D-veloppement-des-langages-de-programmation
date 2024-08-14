/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interfaces;

public interface Inamed extends IAST {
	String getName();
	
	default String getMangledName() {
	    return computeMangledName(getName());
	}
	
	static String computeMangledName(String s) {
        final StringBuffer sb = new StringBuffer(s.length());
        for ( int i=0 ; i<s.length() ; i++ ) {
          final char c = s.charAt(i);
          if (   ( 'a' <= c && c <= 'z' )
              || ( 'A' <= c && c <= 'Z' )
              || ( '0' <= c && c <= '9' )
              || ( '_' == c ) ) {
            sb.append(c);
          } else {
              sb.append("_");
              sb.append(Integer.toHexString(c));
          }
        }
        return sb.toString();
	}
}
