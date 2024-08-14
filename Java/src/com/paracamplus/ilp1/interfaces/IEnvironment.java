/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interfaces;

public interface IEnvironment<K,V,T extends Throwable> {
	/** is the key present in the environment ? */
	boolean isPresent(K key);
	IEnvironment<K,V,T> extend(K key, V value);
	K getKey() throws T;
	V getValue(K key) throws T;
    void update(K key, V value) throws T;
	// Low level interface:
	boolean isEmpty();
	IEnvironment<K,V,T> getNext() throws T;
}
