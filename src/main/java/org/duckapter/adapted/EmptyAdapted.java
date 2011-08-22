package org.duckapter.adapted;

import org.duckapter.Adapted;
import org.duckapter.AdaptedClass;

final class EmptyAdapted<O,D> extends AbstractAdapted<O,D> implements Adapted<O,D> {

	EmptyAdapted(O original, AdaptedClass<O,D> adaptedClass) {
		super(original, adaptedClass);
	}

}
