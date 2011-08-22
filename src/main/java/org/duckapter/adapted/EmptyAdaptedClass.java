package org.duckapter.adapted;

import org.duckapter.AdaptedClass;

final class EmptyAdaptedClass<O,D> extends AbstractEmptyAdaptedClass<O,D> implements AdaptedClass<O,D> {
	
	EmptyAdaptedClass(Class<O> originalClass, Class<D> duckInterface) {
		super(duckInterface, originalClass, false);
	}

}
