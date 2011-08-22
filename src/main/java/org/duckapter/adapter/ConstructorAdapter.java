package org.duckapter.adapter;

import static org.duckapter.adapter.ObjectHandlersFactory.getHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;

/**
 * The adapter for the constructors. Wraps constructor call to be invokable
 * using {@link InvocationAdapter} interface. The adapter is always invokable
 * because its considered static.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.annotation.Constructor
 * @see org.duckapter.checker.ConstructorChecker
 */
public class ConstructorAdapter extends AbstractInvocationAdapter {

	private final Constructor<?> constructor;
	/**
	 * @param duckMethod
	 *            the duck method
	 * @param constructor
	 *            the constructor to be adapted
	 */
	public ConstructorAdapter(Method duckMethod, Constructor<?> constructor) {
		super(duckMethod);
		this.constructor = constructor;
		constructor.setAccessible(true);
	}

	@Override
	protected ObjectHandler initReturnTypeHandler() {
		return getHandler(constructor.getDeclaringClass(), getDuckMethod()
				.getReturnType());
	}

	@Override
	protected ObjectHandler[] initArgumentsHandlers() {
		ObjectHandler[] handlers = new ObjectHandler[getDuckMethod()
				.getParameterTypes().length];
		if (getDuckMethod().getParameterTypes().length == constructor
				.getParameterTypes().length) {
			for (int i = 0; i < getDuckMethod().getParameterTypes().length; i++) {
				handlers[i] = ObjectHandlersFactory.getHandler(getDuckMethod()
						.getParameterTypes()[i], constructor
						.getParameterTypes()[i]);
			}
		}
		return handlers;
	}

	@Override
	public Object doInvoke(Object obj, Object[] args) throws Throwable {
		return constructor.newInstance(args);
	}

	public int getPriority() {
		return InvocationAdaptersPriorities.CONSTRUCTOR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constructor == null) ? 0 : constructor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructorAdapter other = (ConstructorAdapter) obj;
		if (constructor == null) {
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		return true;
	}

	@Override
	public boolean isInvocableOnClass() {
		return true;
	}

	@Override
	public boolean isInvocableOnInstance() {
		return true;
	}

}
