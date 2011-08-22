package org.duckapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Checkers checks whether particular annotated element (class, constructor,
 * method or field) could be adapted to some other annotated duck element (duck
 * interface or method). If necessary one can suppress some other checkers.
 * Checker is usually bind to some annotation which signalizes its usage on the
 * class or method.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 *            annotation binded to the checker
 * @see InvocationAdapter
 */
public interface Checker<T extends Annotation> {

	/**
	 * Return instance of {@link InvocationAdapter} interface which either
	 * denote that checked element pass the checker test or adapts checked
	 * elements to the duck element. For the first case use one of
	 * {@link org.duckapter.adapter.InvocationAdapters} predefined instances.
	 * 
	 * @param anno
	 *            the annotation from the duck element
	 * @param original
	 *            the checked element
	 * @param duck
	 *            the duck element
	 * @param classOfOriginal
	 *            the original class of checked element
	 * @return instance of {@link InvocationAdapter} interface which either
	 *         denote that checked element pass the checker test or adapts
	 *         checked elements to the duck element.
	 */
	InvocationAdapter adapt(T anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal);

}
