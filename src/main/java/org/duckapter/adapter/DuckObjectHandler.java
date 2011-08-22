package org.duckapter.adapter;

import org.duckapter.Duck;

class DuckObjectHandler implements ObjectHandler {

	
	
	public DuckObjectHandler(Class<?> desiredType) {
		this.desiredType = desiredType;
	}

	private final Class<?> desiredType;
	
	public Object handleObject(Object theObject) {
		return Duck.type(theObject, desiredType);
	}

}
