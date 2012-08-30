package org.duckapter.annotation;

import java.lang.annotation.ElementType;

public class ElementTypes {
	
	private ElementTypes(){}

	public static final ElementType[] DEFAULTS = new ElementType[] { 
			ElementType.TYPE,
			ElementType.FIELD, 
			ElementType.METHOD,
			ElementType.CONSTRUCTOR 
	};

}
