package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;
import org.duckapter.LogicalChecker;
import org.duckapter.adapter.InvocationAdapters;

/**
 * The base class for all non-functional (logical) checker. Allows the checker
 * to provide only the logical test without the need to find out the appropriate
 * {@link InvocationAdapter}.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 *            the annotation type of the checker
 */
public abstract class LogicalCheckerBase<T extends Annotation> extends
		AbstractChecker<T> implements LogicalChecker<T> {

	public final InvocationAdapter adapt(T anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		return toMethodAdapter(check(anno, original, duck, classOfOriginal));
	}

	@SuppressWarnings("unchecked")
	public final boolean check(T anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		if (original instanceof Class && duck instanceof Class) {
			return checkClass(anno, (Class) original, (Class) duck);
		}
		if (duck instanceof Method) {
			Method duckMethod = (Method) duck;
			if (original instanceof Field) {
				return checkField(anno, (Field) original, duckMethod,
						classOfOriginal);
			}
			if (original instanceof Method) {
				return checkMethod(anno, (Method) original, duckMethod,
						classOfOriginal);
			}
			if (original instanceof Constructor) {
				return checkConstructor(anno, (Constructor) original,
						duckMethod, classOfOriginal);
			}
		}
		return false;
	}

	/**
	 * Checks the class.
	 * @param anno the annotation from the duck element
	 * @param clazz the class of tested object
	 * @param duckInterface the duck interface
	 * @return whether the class passes the checker test
	 */
	protected boolean checkClass(T anno, Class<?> clazz, Class<?> duckInterface) {
		return false;
	}

	/**
	 * Checks the field. 
	 * @param anno the annotation from the duck method
	 * @param field the tested field
	 * @param duckMethod the duck method
	 * @return whether the field passes the checker test
	 */
	protected boolean checkField(T anno, Field field, Method duckMethod,
			Class<?> classOfOriginal) {
		return false;
	}

	/**
	 * Checks the field. 
	 * @param anno the annotation from the duck method
	 * @param method the method field
	 * @param duckMethod the duck method
	 * @return whether the method passes the checker test
	 */
	protected boolean checkMethod(T anno, Method method, Method duckMethod,
			Class<?> classOfOriginal) {
		return false;
	}

	/**
	 * Checks the constructor. 
	 * @param anno the annotation from the duck method
	 * @param constructor the constructor field
	 * @param duckMethod the duck method
	 * @return whether the constructor passes the checker test
	 */
	protected boolean checkConstructor(T anno, Constructor<?> constructor,
			Method duckMethod, Class<?> classOfOriginal) {
		return false;
	};

	private InvocationAdapter toMethodAdapter(boolean b) {
		return booleanToMethodAdapter(b, InvocationAdapters.OK);
	}

	private InvocationAdapter booleanToMethodAdapter(boolean b,
			InvocationAdapter okAdapter) {
		return b ? okAdapter : InvocationAdapters.NULL;
	}
}
