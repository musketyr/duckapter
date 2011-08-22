package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.adapter.MethodAdapter;

/**
 * The default checker which checks whether the target element is method. It
 * provides the adaptation using the {@link MethodAdapter}.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 *            any checker annotation type
 * @see MethodAdapter
 */
public class MethodsOnlyChecker<T extends Annotation> extends
		AbstractChecker<T> {

	public InvocationAdapter adapt(T anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		if (original instanceof Method && duck instanceof Method) {
			return new MethodAdapter((Method) duck, (Method) original);
		}
		if (original instanceof Class<?>) {
			return InvocationAdapters.OK;
		}
		return InvocationAdapters.NULL;
	};

//	protected Collection<ElementType> getTargetElements(T anno) {
//		if (anno != null) {
//			return super.getTargetElements(anno);
//		}
//		return Arrays.asList(new ElementType[] { ElementType.METHOD,
//				ElementType.CONSTRUCTOR, ElementType.FIELD });
//	};

}
