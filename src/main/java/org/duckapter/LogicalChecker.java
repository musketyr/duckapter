package org.duckapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Interface {@link LogicalChecker} extends the interface {@link Checker} to
 * provide more convenient way to design checkers which instead of functional
 * {@link InvocationAdapter} return just one of
 * {@link org.duckapter.adapter.InvocationAdapters#OK} or
 * {@link org.duckapter.adapter.InvocationAdapters#NULL} to declare that the
 * elements passes or not the check.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 *            the checker annotation type bind to this checker
 */
public interface LogicalChecker<T extends Annotation> extends Checker<T> {

	/**
	 * Checks the element and return whether the elements passes the check.
	 * 
	 * @param anno
	 *            the annotation from the duck method
	 * @param original
	 *            the original element
	 * @param duck
	 *            the element from duck interface or the duck interface itself
	 * @param classOfOriginal
	 *            the class where the original element is declared
	 * @return <code>true</code> if the elements passes the check
	 */
	boolean check(T anno, AnnotatedElement original, AnnotatedElement duck,
			Class<?> classOfOriginal);

}
