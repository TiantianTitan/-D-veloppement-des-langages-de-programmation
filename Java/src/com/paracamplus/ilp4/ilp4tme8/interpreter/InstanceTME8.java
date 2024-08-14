package com.paracamplus.ilp4.ilp4tme8.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.ilp4tme8.interpreter.interfaces.IInstanceTME8;
import com.paracamplus.ilp4.interpreter.ILPInstance;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;

public class InstanceTME8 extends ILPInstance implements IInstanceTME8{

	protected  Map<String, Object> properties;
	
	public InstanceTME8(IClass clazz, Object[] fields) throws EvaluationException {
		super(clazz, fields);
		// TODO Auto-generated constructor stub
		properties= new HashMap<String, Object>();
	}

	@Override
	public Object readProperty(String propertyName) throws EvaluationException {
		try {
			return read(propertyName);
		} catch (EvaluationException e) {
			// TODO: handle exception
			if(!properties.containsKey(propertyName)) {
				throw new EvaluationException("pas de" + propertyName + "!!!");
			}
			return properties.get(propertyName);
		}
		
	}

	@Override
	public Object writeProperty(String propertyName, Object value) throws EvaluationException {
		try {
			return write(propertyName, value);
		} catch (EvaluationException e) {

			Object propertyRet = properties.put(propertyName, value);
		    if(propertyRet == null) return false;
			return propertyRet;
		}
	}

	@Override
	public boolean hasProperty(String propertyName) throws EvaluationException {
		 try{
	            classOf().getOffset(propertyName);
	            return true;
	        }catch (EvaluationException e){

	            return properties.containsKey(propertyName);
	        }
		
//		if (properties.containsKey(propertyName)) return true;
//		return false;
	}

}
