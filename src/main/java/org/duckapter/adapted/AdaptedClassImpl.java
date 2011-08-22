package org.duckapter.adapted;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.duckapter.AdaptationException;
import org.duckapter.AdaptedClass;
import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.MethodAdapter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

final class AdaptedClassImpl<O, D> extends AbstractAdaptedClass<O, D> implements
		AdaptedClass<O, D>, InvocationHandler {

	private final Map<Method, InvocationAdapter> adapters;
	private D proxy;
	private Constructor<?> proxyClassConstructor;
	private AdaptedClass<O, D> detailedClass = null;
	private final boolean detailed;

	private final DuckInterfaceWrapper<D> interfaceWrapper;

	AdaptedClassImpl(Class<O> originalClass, Class<D> duckInterface) {
		this(originalClass, duckInterface, false);
	}

	AdaptedClassImpl(Class<O> originalClass, Class<D> duckInterface,
			boolean detailed) {
		super(duckInterface, originalClass);
		this.detailed = detailed;
		this.interfaceWrapper = DuckInterfaceWrapper.wrap(duckInterface);
		adapters = ImmutableMap.copyOf(collectInvocations());
	}

	private AdaptedClass<O, D> getDetailedClass() {
		if (detailed == true) {
			return this;
		}
		if (detailedClass == null) {
			detailedClass = new AdaptedClassImpl<O, D>(getOriginalClass(),
					getDuckInterface(), true);
			AdaptedFactory.updateCacheInstance(detailedClass);
		}
		return detailedClass;
	}

	public Collection<Method> getUnimplementedForClass() {
		if (detailed) {
			Collection<Method> methods = Lists.newArrayList();
			for (Entry<Method, InvocationAdapter> entry : adapters.entrySet()) {
				if (!entry.getValue().isInvocableOnClass()) {
					methods.add(entry.getKey());
				}
			}
			return methods;
		}
		return getDetailedClass().getUnimplementedForClass();
	}

	public Collection<Method> getUnimplementedForInstance() {
		if (detailed) {
			Collection<Method> methods = Lists.newArrayList();
			for (Entry<Method, InvocationAdapter> entry : adapters.entrySet()) {
				if (!entry.getValue().isInvocableOnInstance()) {
					methods.add(entry.getKey());
				}
			}
			return methods;
		}
		return getDetailedClass().getUnimplementedForClass();
	}

	public Object invoke(Object originalInstance, Method duckMethod,
			Object[] args) throws Throwable {
		return adapters.get(duckMethod).invoke(originalInstance, args);
	}

	private Map<Method, InvocationAdapter> collectInvocations() {
		Map<Method, InvocationAdapter> builder = Maps.newHashMapWithExpectedSize(getDuckInterface().getMethods().length);
		checkClass(builder);
		checkDuckMethods(builder);
		addObjectMethods(builder);
		return builder;
	}

	private void checkClass(Map<Method, InvocationAdapter> builder) {
		updateCanAdapt(interfaceWrapper.getTypeWrapper().resolveAdapter(builder, getOriginalClass()));
	}

	private void addObjectMethods(Map<Method, InvocationAdapter> builder) {
		if (cannotBeAdaptedAnyway()) {
			return;
		}
		for (Method m : Object.class.getMethods()) {
			builder.put(m, new MethodAdapter(m, m));
		}
	}
	
	private void checkDuckMethods(Map<Method, InvocationAdapter> builder) {
		if (cannotBeAdaptedAnyway()) {
			return;
		}
		for (Entry<Method, DuckElementWrapper> duckMethod : interfaceWrapper.getMethodWrappers().entrySet()) {
			InvocationAdapter adapter = duckMethod.getValue().resolveAdapter( builder, getOriginalClass());
			updateCanAdapt(adapter);
			builder.put(duckMethod.getKey(), adapter);
			if (cannotBeAdaptedAnyway()) {
				return;
			}
		}
	}

	private boolean cannotBeAdaptedAnyway() {
		return detailed && (!canAdaptClass && !canAdaptInstance);
	}

	private void updateCanAdapt(InvocationAdapter adapter) {
		canAdaptClass = canAdaptClass && adapter.isInvocableOnClass();
		canAdaptInstance = canAdaptInstance && adapter.isInvocableOnInstance();
	}

	public Map<Method, InvocationAdapter> getAdapters() {
		return adapters;
	}
	
	public D adaptInstance(O instance) {
		if (!canAdaptInstance()) {
			throw new AdaptationException(this);
		}
		return createProxy(instance);
	}

	private D getProxy() {
		if (proxy == null) {
			proxy = createProxy();
		}
		return proxy;
	}

	public D adaptClass() {
		if (!canAdaptClass()) {
			throw new AdaptationException(this);
		}
		return getProxy();
	}

	private D createProxy() {
		return createProxyFor(this);
	}

	private D createProxy(O instance) {
		return createProxyFor(new AdaptedImpl<O, D>(instance, this));
	}

	@SuppressWarnings("unchecked")
	private D createProxyFor(InvocationHandler handler) {
		if (proxyClassConstructor == null) {
			initProxyConstructor();
		}
		try {
			return (D) proxyClassConstructor.newInstance(handler);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IllegalStateException("Cannot create proxy!");
	}

	private void initProxyConstructor() {
		Class<?> proxyClass = Proxy.getProxyClass(AdaptedClassImpl.class
				.getClassLoader(), getDuckInterface());
		try {
			proxyClassConstructor = proxyClass
					.getConstructor(InvocationHandler.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
