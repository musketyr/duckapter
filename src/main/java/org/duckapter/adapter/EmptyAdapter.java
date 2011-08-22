package org.duckapter.adapter;

import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;

/**
 * The base class for the empty adapters which don't wrap any element are e.g.
 * used for discriminating purpose.
 * 
 * @author Vladimir Orany
 * 
 */
public abstract class EmptyAdapter implements InvocationAdapter {

	private final Class<?> returnType;

	public abstract int getPriority();

	public abstract boolean isInvocableOnInstance();

	public abstract boolean isInvocableOnClass();

	/**
	 * @param returnType
	 *            the return type
	 */
	public EmptyAdapter(Class<?> returnType) {
		this.returnType = returnType;
	}

	public InvocationAdapter andMerge(InvocationAdapter other) {
		return InvocationAdapters.andMerge(this, other);
	}

	public InvocationAdapter orMerge(InvocationAdapter other) {
		return InvocationAdapters.orMerge(this, other);
	}

	public Object invoke(Object obj, Object[] args) throws Throwable {
		if (int.class.equals(returnType)) {
			return 0;
		}
		if (short.class.equals(returnType)) {
			return (short) 0;
		}
		if (byte.class.equals(returnType)) {
			return (byte) 0;
		}
		if (long.class.equals(returnType)) {
			return 0L;
		}
		if (char.class.equals(returnType)) {
			return (char) 0;
		}
		if (float.class.equals(returnType)) {
			return 0f;
		}
		if (double.class.equals(returnType)) {
			return 0d;
		}
		if (boolean.class.equals(returnType)) {
			return false;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
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
		EmptyAdapter other = (EmptyAdapter) obj;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}
	
	public Method getDuckMethod() {
		return Default.METHOD;
	}
	
	public void setDuckMethod(Method method) {
		
	}

}