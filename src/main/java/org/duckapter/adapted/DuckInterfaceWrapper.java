package org.duckapter.adapted;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.duckapter.Checker;
import org.duckapter.annotation.ElementTypes;
import org.duckapter.checker.CheckerDescriptor;
import org.duckapter.checker.Checkers;

final class DuckInterfaceWrapper<D>{

	private static final Map<Class<?>, DuckInterfaceWrapper<?>> cache = new HashMap<Class<?>, DuckInterfaceWrapper<?>>();
	
	private Class<D> duckInterface;
	private DuckElementWrapper typeWrapper;
	private Map<Method, DuckElementWrapper> methodsWrappers = new HashMap<Method, DuckElementWrapper>();
	
	public static <T> DuckInterfaceWrapper<T> wrap(Class<T> duckInterface){
		DuckInterfaceWrapper<T> wrapper = (DuckInterfaceWrapper<T>) cache.get(duckInterface);
		if (wrapper != null) {
			return wrapper;
		}
		wrapper = new DuckInterfaceWrapper<T>(duckInterface);
		cache.put(duckInterface, wrapper);
		return wrapper;
		
	}

	private DuckInterfaceWrapper(Class<D> duckInterface) {
		this.duckInterface = duckInterface;
		initTypeCheckers(duckInterface);
		initMethodCheckers(duckInterface);
	}

	private void initTypeCheckers(Class<D> duckInterface) {
		Map<ElementType, Map<Checker, Annotation>> typeCheckers = Checkers.collectCheckers(duckInterface);
		this.typeWrapper = new DuckElementWrapper(
				duckInterface,
				typeCheckers, 
				getMinPriorityToPass(typeCheckers),
				getMinPriorityToFail(typeCheckers));
	}

	private void initMethodCheckers(Class<D> duckInterface) {
		for (Method method : duckInterface.getMethods()) {
			Map<ElementType, Map<Checker, Annotation>> methodCheckers = Checkers.collectCheckers(method);
			this.methodsWrappers.put(method, 
					new DuckElementWrapper(
							method,
							methodCheckers, 
							getMinPriorityToPass(methodCheckers),
							getMinPriorityToFail(methodCheckers))
					
			);
		}
	}

	public Class<D> getDuckInterface() {
		return duckInterface;
	}
	
	public DuckElementWrapper getTypeWrapper() {
		return typeWrapper;
	}
	
	public Map<Method, DuckElementWrapper> getMethodWrappers() {
		return methodsWrappers;
	}
	
	/**
	 * TODO
	 * 
	 * @param checkers
	 * @return
	 */
	public static int getMinPriorityToFail(
			Map<ElementType, Map<Checker, Annotation>> checkers) {
		int ret = Integer.MAX_VALUE;
		for (ElementType elType : ElementTypes.DEFAULTS) {
			for (Entry<Checker, Annotation> e : checkers.get(elType).entrySet()) {
				final int min = CheckerDescriptor.getDescriptor(e.getValue()).getMinToFail();
				if (min < ret) {
					ret = min;
				}
			}
		}
		return ret;
	}

	public static int getMinPriorityToPass(Map<ElementType, Map<Checker, Annotation>> checkers) {
		int ret = Integer.MIN_VALUE;
		for (ElementType elementType : ElementTypes.DEFAULTS) {
			for (Entry<Checker, Annotation> e : checkers.get(elementType).entrySet()) {
				final int max = CheckerDescriptor.getDescriptor(e.getValue()).getMinToPass();
				if (max > ret) {
					ret = max;
				}
			}
		}
		return ret;
	}

}
