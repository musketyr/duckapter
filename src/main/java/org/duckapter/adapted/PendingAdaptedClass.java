package org.duckapter.adapted;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.duckapter.AdaptedClass;
import org.duckapter.InvocationAdapter;

final class PendingAdaptedClass<O,D> implements
		AdaptedClass<O,D> {

	static final AdaptedClass<?,?> NULL_INSTANCE = new PendingAdaptedClass<Object, Object>();

	@SuppressWarnings("unchecked")
	static final <O,D> AdaptedClass<O, D> nullInstance(){
		return (AdaptedClass<O, D>) NULL_INSTANCE;
	}
	
	public D adaptClass() {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public D adaptInstance(O o) {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public boolean canAdaptClass() {
		return true;
	}

	public boolean canAdaptInstance() {
		return true;
	}

	public Class<D> getDuckInterface() {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public Class<O> getOriginalClass() {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public Collection<Method> getUnimplementedForClass() {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public Collection<Method> getUnimplementedForInstance() {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}

	public Object invoke(Object object, Method duckMethod, Object[] args)
			throws Throwable {
		throw new UnsupportedOperationException("Operation not supported on the pending class");
	}
	

	public Map<Method, InvocationAdapter> getAdapters() {
		return Collections.emptyMap();
	}
}
