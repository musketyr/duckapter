package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * The default checker which checks whether the target element is concrete
 * method or constructor.
 * @author Vladimir Orany
 *
 * @param <T> any checker annotation type
 */
public class ConcreteMethodsChecker<T extends Annotation> extends
		LogicalCheckerBase<T> {

	protected boolean checkClass(T anno, java.lang.Class<?> clazz, java.lang.Class<?> duckInterface) {
		return true;
	};
	
	protected boolean checkField(T anno, java.lang.reflect.Field field, Method duckMethod, java.lang.Class<?> classOfOriginal) {
		return true;
	};
	
	@Override
	protected boolean checkMethod(T anno, Method method, Method duckMethod, Class<?> classOfOriginal) {
		return !Modifier.isAbstract(method.getModifiers());
	};

	@Override
	protected boolean checkConstructor(T anno, Constructor<?> con,
			Method duckMethod, Class<?> classOfOriginal) {
		return !Modifier.isAbstract(con.getModifiers());
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
