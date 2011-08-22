package org.duckapter;

import java.lang.reflect.Method;

/**
 * Interface {@link InvocationAdapter} describes adapters which adapt particular
 * element from original class (method, constructor or field) to the method from
 * the duck interface and/or say whether the adaptation is possible using the
 * {@link #isInvocableOnClass()} and the {@link #isInvocableOnInstance()}
 * methods.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.adapter.InvocationAdaptersPriorities
 * @see org.duckapter.adapter.InvocationAdapters
 */
public interface InvocationAdapter {

	/**
	 * Perform the desired invocation or change on the supplied object.
	 * 
	 * @param obj
	 *            the object to be the invocation performed
	 * @param args
	 *            the arguments to the invocation
	 * @return the result of invocation
	 * @throws Throwable
	 *             if an exception occurs during the invocation
	 */
	Object invoke(Object obj, Object[] args) throws Throwable;

	/**
	 * @return the priority of the adapter which helps to find the best adapter
	 *         for single method for the duck interface
	 * @see org.duckapter.adapter.InvocationAdaptersPriorities
	 */
	int getPriority();

	/**
	 * Merges the adapter with the other one. The result is usually the one
	 * having higher priority.
	 * @param other the other adapter to be merge with
	 * @return result of the merger, usually the one with higher priority
	 */
	InvocationAdapter orMerge(InvocationAdapter other);

	/**
	 * Merges the adapter with the other one. The result is usually the one
	 * having lower priority.
	 * @param other the other adapter to be merge with
	 * @return result of the merger, usually the one with lower priority
	 */
	InvocationAdapter andMerge(InvocationAdapter other);

	/**
	 * @return whether the invocation can be performed on the instance level
	 */
	boolean isInvocableOnInstance();

	/**
	 * @return whether the invocation can be performed on the class level
	 */
	boolean isInvocableOnClass();
	
	/**
	 * @return duck method adapted by this adapter
	 */
	Method getDuckMethod();
	
	/**
	 * sets different method to be adapted by this adapter
	 * @param method
	 */
	void setDuckMethod(Method method);
	
	public static class Default {
		public static final Method METHOD;
		static {
			Method method = null;
			try {
				method = InvocationAdapter.class.getDeclaredMethod("getDuckMethod", new Class[0]);
			} catch (Exception e) {
				throw new IllegalStateException("Missing method getDuckMethod!");
			}
			METHOD = method;
		}
	}
	
	

}
