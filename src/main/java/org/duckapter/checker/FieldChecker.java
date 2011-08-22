package org.duckapter.checker;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.GetFieldAdapter;
import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.adapter.SetFieldAdapter;
import org.duckapter.annotation.Field;

/**
 * Checker for the {@link Field} annotation.
 * 
 * @author Vladimir Orany
 * @see Field
 * @see SetFieldAdapter
 * @see GetFieldAdapter
 */
public class FieldChecker extends AbstractChecker<Field> {

	public InvocationAdapter adapt(Field anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		if (original instanceof java.lang.reflect.Field
				&& duck instanceof Method) {
			final Method duckMethod = (Method) duck;
			final java.lang.reflect.Field field = (java.lang.reflect.Field) original;
			if (duckMethod.getParameterTypes().length == 1
					&& !Modifier.isFinal(field.getModifiers())) {
				return new SetFieldAdapter(duckMethod, field);
			} else if (duckMethod.getParameterTypes().length == 0) {
				return new GetFieldAdapter(duckMethod, field);
			}
		}
		return InvocationAdapters.NULL;
	}

}
