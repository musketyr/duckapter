package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.duckapter.Duck;
import org.duckapter.adapted.AdaptedFactory;

/**
 * Default checker which checks whether the target element's return type is
 * compatible with the return type of the duck method. The target element's
 * return type is compatible if is assignable to the return type of the duck
 * method or can be adapted using {@link Duck#type(Class, Class)} or
 * {@link Duck#type(Object, Class)} methods.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 */
public class ReturnTypeChecker<T extends Annotation> extends
		LogicalCheckerBase<T> {

	protected boolean checkConstructor(T anno, Constructor<?> constructor,
			Method duckMethod, Class<?> classOfOriginal) {
		if (duckMethod.getDeclaringClass().isAssignableFrom(
				duckMethod.getReturnType())) {
			return true;
		}
		return checkDuck(constructor.getDeclaringClass(), duckMethod);
	}

	private boolean checkDuck(Class<?> returnType, Method duckMethod) {
		if (void.class.equals(duckMethod.getReturnType())) {
			return true;
		}
		if (void.class.equals(returnType)) {
			return false;
		}
		if (Object.class.equals(duckMethod.getReturnType())) {
			return true;
		}
		if (duckMethod.getReturnType().isAssignableFrom(returnType)) {
			return true;
		}
		if (duckMethod.getReturnType().isPrimitive()
				&& !returnType.isPrimitive()) {
			return false;
		}
		if (!duckMethod.getReturnType().isInterface()) {
			return false;
		}
		return AdaptedFactory.adapt(returnType, duckMethod.getReturnType())
				.canAdaptInstance();
	};
//
//	@Override
//	protected Collection<ElementType> getTargetElements(T anno) {
//		if (anno != null) {
//			return super.getTargetElements(anno);
//		}
//		return Arrays.asList(new ElementType[] { ElementType.METHOD,
//				ElementType.CONSTRUCTOR, ElementType.FIELD });
//	};

	protected boolean checkClass(T anno, java.lang.Class<?> clazz, java.lang.Class<?> duckInterface) { return true;};
	
	@Override
	protected boolean checkField(T anno, Field field, Method duckMethod,
			Class<?> classOfOriginal) {
		if (duckMethod.getDeclaringClass().isAssignableFrom(
				duckMethod.getReturnType())
				&& field.getDeclaringClass().isAssignableFrom(field.getType())) {
			return true;
		}
		return checkDuck(field.getType(), duckMethod);
	};

	@Override
	protected boolean checkMethod(T anno, Method method, Method duckMethod,
			Class<?> classOfOriginal) {
		if (duckMethod.getDeclaringClass().isAssignableFrom(
				duckMethod.getReturnType())
				&& method.getDeclaringClass().isAssignableFrom(
						method.getReturnType())) {
			return true;
		}
		return checkDuck(method.getReturnType(), duckMethod);
	};

}
