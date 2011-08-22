package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.annotation.AnnotatedWith;

/**
 * The default checker which checks that the duck and target elements have the
 * same non-checker annotations. Suppresses {@link NameChecker} if there is
 * some non-checker annotation placed on the duck element (method or class). 
 * 
 * @author Vladimir Orany
 *
 * @param <T> any checker annotation type
 */
public class AnnotatedWithChecker extends
		AbstractChecker<AnnotatedWith> {

	protected Collection<ElementType> getTargetElements(AnnotatedWith anno) {
		return ALL_TARGETS;
	}

	public InvocationAdapter adapt(AnnotatedWith anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		if (!hasRelevantAnnotations(duck)) {
			return InvocationAdapters.OK;
		}
		Collection<Annotation> fromDuck = collectAnnotations(duck);
		Collection<Annotation> fromOriginal = collectAnnotations(original);
		if (fromDuck.equals(fromOriginal)) {
			return InvocationAdapters.OK;
		}
		return InvocationAdapters.NULL;
	}

	private Collection<Annotation> collectAnnotations(AnnotatedElement duck) {
		Collection<Annotation> fromDuck = new ArrayList<Annotation>();
		for (Annotation a : duck.getAnnotations()) {
			if (!CheckerDescriptor.getDescriptor(a).isValid()) {
				fromDuck.add(a);
			}
		}
		return fromDuck;
	};

	private boolean hasRelevantAnnotations(AnnotatedElement element) {
		for (Annotation anno : element.getAnnotations()) {
			if (!CheckerDescriptor.getDescriptor(anno).isValid()) {
				return true;
			}
		}
		return false;
	}

}
