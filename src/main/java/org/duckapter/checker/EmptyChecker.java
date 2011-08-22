package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;

import org.duckapter.Checker;
import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.InvocationAdapters;

/**
 * This is the <code>null</code> object for the {@link InvocationAdapter}
 * interface.
 * 
 * @author Vladimir Orany
 * 
 * @param <T> any checker annotation type
 */
public class EmptyChecker<T extends Annotation> extends AbstractChecker<T> {

	private static final EmptyChecker<Annotation> INSTANCE = new EmptyChecker<Annotation>();

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> Checker<A> getInstance() {
		return (EmptyChecker<A>) INSTANCE;
	}

	public InvocationAdapter adapt(T anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		return InvocationAdapters.OK;
	}

	protected Collection<ElementType> getTargetElements(T anno) {
		return Collections.emptyList();
	};

}
