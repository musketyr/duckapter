package org.duckapter.adapted;

import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class AdaptedClassHelper {
	
	private AdaptedClassHelper() {
		// prevents instance creation and subtyping
	}
	
	static Collection<AnnotatedElement> getRelevantElements(Class<?> clazz) {
		Collection<AnnotatedElement> elements = new ArrayList<AnnotatedElement>();
		elements.addAll(getRelevantConstructors(clazz));
		elements.addAll(getRelevantFields(clazz));
		elements.addAll(getRelevantMethods(clazz));
		return elements;
	}
	
	@SuppressWarnings("unchecked")
	static Collection<? extends AnnotatedElement> getRelevantElements(ElementType elType, Class<?> clazz) {
		switch (elType) {
		case CONSTRUCTOR:
			return getRelevantConstructors(clazz);
		case FIELD:
			return getRelevantFields(clazz);
		case METHOD:
			return getRelevantMethods(clazz);
		case TYPE:
			return Arrays.asList(clazz);
		default:
			return Collections.emptyList();
		}
	}

	private static Map<String, Field> getFields(Class<?> clazz,
			boolean exceptPrivate) {
		Map<String, Field> ret = new LinkedHashMap<String, Field>();
		for (Field f : clazz.getDeclaredFields()) {
			if (!exceptPrivate || !Modifier.isPrivate(f.getModifiers())) {
				ret.put(f.getName(), f);
			}
		}
		return ret;
	}

	private static Map<MethodSignature, Method> getMethods(Class<?> clazz,
			boolean exceptPrivate) {
		Map<MethodSignature, Method> ret = new LinkedHashMap<MethodSignature, Method>();
		for (Method m : clazz.getDeclaredMethods()) {
			if (!exceptPrivate || !Modifier.isPrivate(m.getModifiers())) {
				ret
						.put(new MethodSignature(m.getParameterTypes(), m
								.getName()), m);
			}
		}
		return ret;
	}

	static Collection<Constructor<?>> getRelevantConstructors(
			Class<?> original) {
		return Arrays.asList(original.getDeclaredConstructors());
	}

	static Collection<Field> getRelevantFields(Class<?> original) {
		Map<String, Field> fields = new LinkedHashMap<String, Field>();
		for (Class<?> c : getSuperClasses(original)) {
			fields.putAll(getFields(c, true));
		}
		for (Field f : original.getDeclaredFields()) {
			fields.put(f.getName(), f);
		}
		return fields.values();
	}

	static Collection<Method> getRelevantMethods(Class<?> original) {
		Map<MethodSignature, Method> methods = new LinkedHashMap<MethodSignature, Method>();
		for (Class<?> c : getSuperClasses(original)) {
			methods.putAll(getMethods(c, true));
		}
		for (Method m : original.getDeclaredMethods()) {
			methods.put(
					new MethodSignature(m.getParameterTypes(), m.getName()), m);
		}
		return methods.values();
	}

	private static List<Class<?>> getSuperClasses(Class<?> clazz) {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		if (clazz.getSuperclass() != null) {
			ret.addAll(getSuperClasses(clazz.getSuperclass()));
			ret.add(clazz.getSuperclass());
		}
		return ret;
	}
	
}
