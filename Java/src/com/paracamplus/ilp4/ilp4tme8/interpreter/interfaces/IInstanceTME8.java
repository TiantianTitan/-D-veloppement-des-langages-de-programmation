package com.paracamplus.ilp4.ilp4tme8.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IInstance;

public interface IInstanceTME8 extends IInstance{

	Object readProperty(String propertyName) throws EvaluationException;
	Object writeProperty(String propertyName,Object value) throws EvaluationException;
	boolean hasProperty(String propertyName) throws EvaluationException;
	
	
}
