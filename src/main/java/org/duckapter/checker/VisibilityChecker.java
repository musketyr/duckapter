package org.duckapter.checker;

import static org.duckapter.checker.Checkers.getModifiers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.duckapter.Duck;
import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.InvocationAdapters;

/**
 * Common checker for the visibility annotations
 * {@link org.duckapter.annotation.Private},
 * {@link org.duckapter.annotation.Protected}
 * {@link org.duckapter.annotation.Package} and
 * {@link org.duckapter.annotation.Public}.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.annotation.Private
 * @see org.duckapter.annotation.Protected
 * @see org.duckapter.annotation.Package
 * @see org.duckapter.annotation.Public
 * @see Visibility
 * 
 */
public class VisibilityChecker extends AbstractChecker<Annotation> {

	private static interface VisibilityAnno {
		Visibility value();
	}

	public final InvocationAdapter adapt(Annotation anno,
			AnnotatedElement original, AnnotatedElement duck,
			Class<?> classOfOriginal) {
		return getVisibility(anno).check(anno, getModifiers(original)) ? InvocationAdapters.OK
				: InvocationAdapters.NULL;
	}

	protected Visibility getVisibility(Annotation anno) {
		if (!Duck.test(anno, VisibilityAnno.class)) {
			throw new IllegalArgumentException("Cannot adapt annotation: "
					+ anno);
		}
		return Duck.type(anno, VisibilityAnno.class).value();
	}

}
