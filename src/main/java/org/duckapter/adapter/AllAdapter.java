package org.duckapter.adapter;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.duckapter.InvocationAdapter;
import org.duckapter.annotation.All;
import org.duckapter.checker.AllChecker;

/**
 * The adapter for {@link All} annotation and {@link AllChecker}. Collects the
 * best adapters for each duck method and original element pair. The adapter is
 * always invokable because the resulting array can be empty.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.annotation.All
 * @see org.duckapter.checker.AllChecker
 */
public class AllAdapter implements InvocationAdapter {

	private Method returnsTypeMethod;

	private InvocationAdapter adapter = InvocationAdapters.MAX;
	private AllAdapter previous = null;

	/**
	 * @param element
	 *            the annotated element, usually the duck method
	 * @param returnsTypeMethod
	 *            the single method from the return type's interface
	 */
	public AllAdapter(AnnotatedElement element, Method returnsTypeMethod) {
		this.returnsTypeMethod = returnsTypeMethod;
	}

	public int getPriority() {
		return InvocationAdaptersPriorities.ALL;
	}

	public InvocationAdapter andMerge(InvocationAdapter other) {
		adapter = InvocationAdapters.andMerge(adapter, other);
		adapter.setDuckMethod(returnsTypeMethod);
		return this;
	}

	public InvocationAdapter orMerge(InvocationAdapter theOther) {
		if (theOther.getPriority() > getPriority()) {
			return theOther;
		}
		if (theOther instanceof AllAdapter) {
			((AllAdapter) theOther).previous = this;
			return theOther;
		}
		return this;
	}

	public Object invoke(Object obj, Object[] args) throws Throwable {
		return collectProxiesArray(obj, returnsTypeMethod.getDeclaringClass());
	}

	@SuppressWarnings("unchecked")
	private <T> T[] collectProxiesArray(Object obj, Class<T> footPrint) {
		return collectProxies(obj, footPrint).toArray(
				(T[]) Array.newInstance(footPrint, 0));
	}

	private <T> List<T> collectProxies(Object obj, Class<T> footPrint) {
		List<T> ret = new ArrayList<T>();
		AllAdapter ama = this;
		while (ama != null) {
			if (obj == null && ama.adapter.isInvocableOnClass()) {
				ret.add(createProxy(ama.adapter, null, footPrint));
			}
			if (obj != null && ama.adapter.isInvocableOnInstance()) {
				ret.add(createProxy(ama.adapter, obj, footPrint));
			}
			ama = ama.previous;
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private <T> T createProxy(final InvocationAdapter adapter,
			final Object obj, Class<T> footPrint) {
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class<?>[] { footPrint }, new InvocationHandler() {

					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						if (returnsTypeMethod.equals(method)) {
							return adapter.invoke(obj, args);
						}
						return null;
					}
				});
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[return type method="
				+ returnsTypeMethod.getDeclaringClass().getSimpleName() + "."
				+ returnsTypeMethod.getName() + "(*), adapter=" + adapter
				+ ", previous=" + previous;
	}

	public boolean isInvocableOnClass() {
		return true;
	}

	public boolean isInvocableOnInstance() {
		return true;
	}

	public Method getDuckMethod() {
		return returnsTypeMethod;
	}

	public void setDuckMethod(Method method) {
		this.returnsTypeMethod = method;
	}

}
