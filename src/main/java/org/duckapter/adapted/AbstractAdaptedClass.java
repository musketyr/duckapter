package org.duckapter.adapted;

import org.duckapter.AdaptedClass;

abstract class AbstractAdaptedClass<O,D> implements AdaptedClass<O,D> {

	protected boolean canAdaptClass = true;
	protected boolean canAdaptInstance = true;
	private final Class<D> duckInterface;
	private final Class<O> originalClass;



	public AbstractAdaptedClass(final Class<D> duckInterface, final Class<O> originalClass) {
		this.duckInterface = duckInterface;
		this.originalClass = originalClass;
	}

	public boolean canAdaptClass() {
		return canAdaptClass;
	}

	public boolean canAdaptInstance() {
		return canAdaptInstance;
	}

	public Class<D> getDuckInterface() {
		return duckInterface;
	}

	public Class<O> getOriginalClass() {
		return originalClass;
	}


}