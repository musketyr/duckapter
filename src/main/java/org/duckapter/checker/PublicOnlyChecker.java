package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Default checker which checks whether the target element is declared public.
 * 
 * @author Vladimir Orany
 * 
 */
public class PublicOnlyChecker<T extends Annotation> extends
		LogicalCheckerBase<T> {

	protected boolean checkClass(T anno, java.lang.Class<?> clazz, java.lang.Class<?> duckInterface) {
		return Modifier.isPublic(clazz.getModifiers());
	};
	
	protected boolean checkField(T anno, java.lang.reflect.Field field, Method duckMethod, java.lang.Class<?> classOfOriginal) {
		return Modifier.isPublic(field.getModifiers());
	};
	
	@Override
	protected boolean checkMethod(T anno, Method method, Method duckMethod, Class<?> classOfOriginal) {
		return Modifier.isPublic(method.getModifiers());
	};

	@Override
	protected boolean checkConstructor(T anno, Constructor<?> con,
			Method duckMethod, Class<?> classOfOriginal) {
		return Modifier.isPublic(con.getModifiers());
	};
}
