package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.duckapter.Checker;
import org.duckapter.annotation.ElementTypes;

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
	private static final Collection<? extends Checker> defaultCheckers;
	
	static {
	    Collection<Checker> list = new ArrayList<Checker>();
	    list.add(new NameChecker<Annotation>());
	    list.add(new MethodsOnlyChecker<Annotation>());
	    list.add(new PublicOnlyChecker<Annotation>());
	    list.add(new ConcreteMethodsChecker<Annotation>());
	    list.add(new ParametersChecker<Annotation>());
	    list.add(new ReturnTypeChecker<Annotation>());
	    list.add(new ExceptionsChecker<Annotation>());
	    defaultCheckers = Collections.unmodifiableCollection(list);
	}

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


	public static Map<ElementType, Map<Checker, Annotation>> initCheckers(){
		Map<ElementType, Map<Checker, Annotation>> checkers = new HashMap<ElementType, Map<Checker,Annotation>>();
		for (ElementType el : ElementTypes.DEFAULTS) {
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
			for (ElementType elType : ElementTypes.DEFAULTS) {
				checkers.get(elType).put(ch, null);
			}
		}
		Set<Entry<Checker, Annotation>> entries = new HashSet<Entry<Checker,Annotation>>();
		for (ElementType elType : ElementTypes.DEFAULTS) {
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
