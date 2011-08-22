package org.duckapter.adapted;

import org.duckapter.Adapted;
import org.duckapter.AdaptedClass;

abstract class AbstractAdapted<O,D> implements Adapted<O,D> {

	private final O original;
	private final AdaptedClass<O,D> adaptedClass;

	AbstractAdapted(O original, AdaptedClass<O,D> adaptedClass) {
		this.original = original;
		this.adaptedClass = adaptedClass;
	}

	public AdaptedClass<O,D> getAdaptedClass() {
		return adaptedClass;
	}

	public O getOriginalInstance() {
		return original;
	}

}