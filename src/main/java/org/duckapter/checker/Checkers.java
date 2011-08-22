package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.duckapter.Checker;
import org.duckapter.annotation.CanCheck;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

/**
 * Utility class for checkers.
 * 
 * @author Vladimir Orany
 * 
 */
public final class Checkers {

	private Checkers() {
		// prevents instance creation and subtyping
	}

	@SuppressWarnings("unchecked")
	private static final Collection<? extends Checker> defaultCheckers = ImmutableList.of(
			new ExceptionsChecker<Annotation>(), new NameChecker<Annotation>(),
			new MethodsOnlyChecker<Annotation>(), new PublicOnlyChecker<Annotation>(),
			new ConcreteMethodsChecker<Annotation>(),
			new ParametersChecker<Annotation>(),
			new ReturnTypeChecker<Annotation>());

	/**
	 * Return the collection of the default checkers which are always used until
	 * suppressed by
	 * {@link Checker#suppressCheckers(Annotation, AnnotatedElement)} method.
	 * The default checker are listed under the see tag.
	 * 
	 * @param <T>
	 *            common checker annotation of all default checkers
	 * @return the collection of all default checkers
	 * @see AnnotatedWithChecker
	 * @see ExceptionsChecker
	 * @see NameChecker
	 * @see MethodsOnlyChecker
	 * @see PublicOnlyChecker
	 * @see ConcreteMethodsChecker
	 * @see ParametersChecker
	 * @see ReturnTypeChecker
	 */
	@SuppressWarnings("unchecked")
	public static final Collection<Checker> getDefaultCheckers() {
		return (Collection<Checker>) defaultCheckers;
	}


	/**
	 * Returns modifiers for specified element.
	 * 
	 * @param original
	 *            the element
	 * @return modifiers for the specified element
	 */
	public static int getModifiers(AnnotatedElement original) {
		if (original instanceof Class<?>) {
			return ((Class<?>) original).getModifiers();
		}
		if (original instanceof Member) {
			return ((Member) original).getModifiers();
		}
		throw new IllegalArgumentException("Illegal argument: " + original);
	}

	/**
	 * Optimized method for the checkers.
	 * 
	 * @param ch
	 *            the checker
	 * @param obj
	 *            the other object
	 * @return if the checker equals the other object
	 */
	public static boolean equals(Checker<?> ch, Object obj) {
		if (obj == null) {
			return false;
		}
		if (ch == obj) {
			return true;
		}
		return ch.hashCode() == obj.hashCode();
	}


	
	/**
	 * Return hash
	 * 
	 * @param clazz
	 * @return
	 */
	public static int hashCode(Class<?> clazz) {
		return 37 + 37 * clazz.getName().hashCode();
	}

	/**
	 * Return hash code for the checker based on common convention.
	 * 
	 * @param ch
	 *            the checker
	 * @return the hash code for the checker
	 */
	public static int hashCode(Checker<?> ch) {
		return 37 + 37 * ch.getClass().getName().hashCode();
	}


	public static EnumMap<ElementType, Map<Checker, Annotation>> initCheckers(){
		EnumMap<ElementType, Map<Checker, Annotation>> checkers = Maps.newEnumMap(ElementType.class);
		for (ElementType el : CanCheck.DEFAULTS) {
			checkers.put(el, new HashMap<Checker, Annotation>());
		}
		return checkers;
	}


	public static Map<ElementType, Map<Checker, Annotation>> collectCheckers(AnnotatedElement element) {
		Map<ElementType, Map<Checker, Annotation>> checkers = initCheckers();
		for (Annotation anno : element.getAnnotations()) {
			CheckerDescriptor desc = CheckerDescriptor.getDescriptor(anno);
			if (desc.isValid()) {
				for (ElementType elType : desc.getCanAdapt()) {
					checkers.get(elType).put(desc.getChecker(), anno);
				}
			}
		}
		for (Checker<?> ch : getDefaultCheckers()) {
			for (ElementType elType : CanCheck.DEFAULTS) {
				checkers.get(elType).put(ch, null);
			}
		}
		Set<Entry<Checker, Annotation>> entries = new HashSet<Entry<Checker,Annotation>>();
		for (ElementType elType : CanCheck.DEFAULTS) {
			entries.addAll(checkers.get(elType).entrySet());
		}
		Set<CheckerDescriptor> suppressedMethodChecker = new HashSet<CheckerDescriptor>();
		for (Entry<Checker, Annotation> e : entries) {
			for (Class<? extends Checker> clazz : CheckerDescriptor.getDescriptor(e.getValue()).getSuppressed()) {
				CheckerDescriptor descriptor = CheckerDescriptor.getDescriptor(clazz);
				suppressedMethodChecker.add(descriptor);
			}
		}
		for (CheckerDescriptor checkerDescriptor : suppressedMethodChecker) {
			for (ElementType elType : checkerDescriptor.getCanAdapt()) {
				checkers.get(elType).remove(checkerDescriptor.getChecker());
			}
		}
		return checkers;
	}

}
