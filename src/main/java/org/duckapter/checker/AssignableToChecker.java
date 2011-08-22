package org.duckapter.checker;

import java.lang.reflect.AnnotatedElement;

import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.annotation.AssignableTo;
/**
 * Checker for the {@link AssignableTo} annotation.
 * @author Vladimir Orany
 * @see AssignableTo
 */
public class AssignableToChecker extends AbstractChecker<AssignableTo> {

	@SuppressWarnings("unchecked")
	public InvocationAdapter adapt(AssignableTo anno,
			AnnotatedElement original, AnnotatedElement duck,
			Class<?> classOfOriginal) {
		if (original instanceof Class) {
			Class clazz = (Class) original;
			boolean isAssignable = true;
			for (Class val : anno.value()) {
				isAssignable = isAssignable && val.isAssignableFrom(clazz);
			}
			return isAssignable ? InvocationAdapters.OK
					: InvocationAdapters.NULL;
		} else {
			return InvocationAdapters.NULL;
		}
	}

}
