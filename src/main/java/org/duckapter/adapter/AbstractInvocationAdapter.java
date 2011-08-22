package org.duckapter.adapter;

import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;

/**
 * This class helps creating new implementations of {@link InvocationAdapter}
 * interface. It provides methods for handling input parameters and also the
 * return value. Merge methods {@link #orMerge(InvocationAdapter)} and
 * {@link #andMerge(InvocationAdapter)} delegate its function to the
 * {@link InvocationAdapters} implementations. The methods
 * {@link #isInvocableOnClass()} and {@link #isInvocableOnInstance()} return
 * <code>true</code> by default.
 * 
 * @author Vladimir Orany
 * 
 */
public abstract class AbstractInvocationAdapter implements InvocationAdapter {


	private Method duckMethod;
	private ObjectHandler returnTypeHandler = null;
	private ObjectHandler[] argumentsHandlers = null;

	public AbstractInvocationAdapter(Method duckMethod) {
		this.duckMethod = duckMethod;
	}

	public final Object invoke(Object obj, Object[] args) throws Throwable {
		return handleReturnType(doInvoke(obj, handleArgs(args)));
	}

	/**
	 * Perform the invocation on the adapted element.
	 * 
	 * @param obj
	 *            the object to be the invocation performed
	 * @param args
	 *            prepared method arguments
	 * @return result of the invocation to be handled by this class and returned
	 * @throws Throwable
	 *             if exception occurs during the invocation
	 */
	protected abstract Object doInvoke(Object obj, Object[] args)
			throws Throwable;

	private ObjectHandler getReturnTypeHandler(){
		if (returnTypeHandler == null) {
			returnTypeHandler = initReturnTypeHandler();
		}
		return returnTypeHandler;
	}

	private ObjectHandler[] getArgumentsHandlers(){
		if (argumentsHandlers == null) {
			argumentsHandlers = initArgumentsHandlers();
		}
		return argumentsHandlers;
	}

	private final Object[] handleArgs(Object[] args) {
		if (args == null) {
			return new Object[0];
		}
		ObjectHandler[] handlers = getArgumentsHandlers();
		Object[] duckedArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			duckedArgs[i] = handlers[i].handleObject(args[i]);
		}
		return duckedArgs;
	}



	public InvocationAdapter orMerge(InvocationAdapter other) {
		return InvocationAdapters.orMerge(this, other);
	}

	public InvocationAdapter andMerge(InvocationAdapter other) {
		return InvocationAdapters.andMerge(this, other);
	}

	protected abstract ObjectHandler[] initArgumentsHandlers();

	protected abstract ObjectHandler initReturnTypeHandler();

	private final Object handleReturnType(Object ret) {
		return getReturnTypeHandler().handleObject(ret);
	}

	public boolean isInvocableOnClass() {
		return true;
	}

	public boolean isInvocableOnInstance() {
		return true;
	}

	public Method getDuckMethod() {
		return duckMethod;
	}
	
	public void setDuckMethod(Method method) {
		this.duckMethod = method;
		this.returnTypeHandler = null;
		this.argumentsHandlers = null;
	}

}
