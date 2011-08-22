package org.duckapter.adapted;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.duckapter.Adapted;
import org.duckapter.AdaptedClass;

final class AdaptedImpl<O, D> extends AbstractAdapted<O, D> implements
		Adapted<O, D>, InvocationHandler {

	AdaptedImpl(O originalInstance, AdaptedClass<O, D> adaptedClass) {
		super(originalInstance, adaptedClass);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return getAdaptedClass().invoke(getOriginalInstance(), method, args);
	}

}
