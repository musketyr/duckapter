package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * The default checker which checks whether the target elements' declared
 * exceptions are compatible with the ones declared by the duck method.
 * @author Vladimir Orany
 *
 * @param <T> any checker annotation type
 */
public class ExceptionsChecker<T extends Annotation> extends
		LogicalCheckerBase<T> {

	protected boolean checkMethod(T anno, Method method, Method duckMethod, Class<?> classOfOriginal) {
		return checkExes(method.getExceptionTypes(), duckMethod
				.getExceptionTypes());
	}
	
	protected boolean checkClass(T anno, java.lang.Class<?> clazz, java.lang.Class<?> duckInterface) {
		return true;
	};
	
	protected boolean checkField(T anno, java.lang.reflect.Field field, Method duckMethod, java.lang.Class<?> classOfOriginal) {
		return true;
	};

	private boolean checkExes(final Class<?>[] originalExs,
			final Class<?>[] duckExs) {
		if (originalExs.length == 0) {
			return true;
		}
		if (duckExs.length == 0 && originalExs.length != 0) {
			return false;
		}
		for (Class<?> originalEx : originalExs) {
			boolean exOk = false;
			for (Class<?> duckEx : duckExs) {
				exOk |= duckEx.isAssignableFrom(originalEx);
			}
			if (!exOk) {
				return false;
			}
		}
		return true;
	};

	protected boolean checkConstructor(T anno, Constructor<?> constructor,
			Method duckMethod, Class<?> classOfOriginal) {
		return checkExes(constructor.getExceptionTypes(), duckMethod
				.getExceptionTypes());
	};

//	@Override
//	protected Collection<ElementType> getTargetElements(T anno) {
//		if (anno != null) {
//			return super.getTargetElements(anno);
//		}
//		return Arrays.asList(new ElementType[] { ElementType.METHOD,
//				ElementType.CONSTRUCTOR });
//	};

}
