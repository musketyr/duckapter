package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.duckapter.Checker;
import org.duckapter.CheckerAnnotation;
import org.duckapter.Duck;
import org.duckapter.adapter.InvocationAdaptersPriorities;
import org.duckapter.annotation.CanCheck;
import org.duckapter.annotation.MinToFail;
import org.duckapter.annotation.MinToPass;
import org.duckapter.annotation.SuppressChecker;

import com.google.common.collect.ImmutableList;

public final class CheckerDescriptor {

	
	private static interface CheckerWithPriority {
		int checkerPriority();
	}
	
	@MinToFail(InvocationAdaptersPriorities.NONE)
	@MinToPass(InvocationAdaptersPriorities.METHOD)
	private @interface NullDescriptorHelper{}

	private static final Map<Class<? extends Annotation>, CheckerDescriptor> byAnnoTypeCache = new HashMap<Class<? extends Annotation>, CheckerDescriptor>();
	
	@SuppressWarnings("unchecked")
	private static final Map<Class<? extends Checker>, CheckerDescriptor> byClassCache = new HashMap<Class<? extends Checker>, CheckerDescriptor>();

	public static final CheckerDescriptor NULL_DESCRIPTOR = new CheckerDescriptor(new NullDescriptorHelper(){
		public Class<? extends Annotation> annotationType() {
			return NullDescriptorHelper.class;
		}
	});

	private static int getAnnoPriority(Annotation toReturn) {
		if (Duck.test(toReturn, CheckerWithPriority.class)) {
			return Duck.type(toReturn, CheckerWithPriority.class)
					.checkerPriority();
		}
		return Integer.MIN_VALUE;
	}

	public static final CheckerDescriptor getDescriptor(Annotation annotation) {
		if (annotation == null) {
			return NULL_DESCRIPTOR;
		}
		final Class<? extends Annotation> anno = annotation.annotationType();
		CheckerDescriptor desc = byAnnoTypeCache.get(anno);
		if (desc != null) {
			return desc;
		}
		desc = new CheckerDescriptor(annotation);
		byAnnoTypeCache.put(anno, desc);
		byClassCache.put(desc.getChecker().getClass(), desc);
		return desc;
	}
	
	@SuppressWarnings("unchecked")
	public static final CheckerDescriptor getDescriptor(Class<? extends Checker> checkerClass) {
		if (checkerClass == null) {
			return NULL_DESCRIPTOR;
		}
		CheckerDescriptor desc = byClassCache.get(checkerClass);
		if (desc != null) {
			return desc;
		}
		desc = new CheckerDescriptor(checkerClass);
		byClassCache.put(checkerClass, desc);
		return desc;
	}
	
	private static CheckerAnnotation getCheckerAnno(
			Class<? extends Annotation> anno,
			Class<? extends Annotation>... exclude) {
		if (anno.isAnnotationPresent(CheckerAnnotation.class)) {
			return anno.getAnnotation(CheckerAnnotation.class);
		}
		Collection<Class<? extends Annotation>> toTest = new ArrayList<Class<? extends Annotation>>();
		final List<Class<? extends Annotation>> excludeList = Arrays
				.asList(exclude);
		final List<Annotation> withCheckerAnnotation = new ArrayList<Annotation>();
		for (Annotation a : anno.getAnnotations()) {
			if (excludeList.contains(a)) {
				continue;
			}
			if (a.annotationType().isAnnotationPresent(CheckerAnnotation.class)) {
				withCheckerAnnotation.add(a);
			} else {
				for (Annotation annoToTest : a.annotationType()
						.getAnnotations()) {
					toTest.add(annoToTest.annotationType());
				}
				toTest.remove(a);
			}
		}
		if (!withCheckerAnnotation.isEmpty()) {
			return getWithHighestPriority(withCheckerAnnotation);
		}
		toTest.remove(anno);
		toTest.removeAll(excludeList);
		return testForCheckerAnno(toTest, excludeList);
	}

	/**
	 * Return shared instance for the specified checker class.
	 * 
	 * @param <A>
	 *            the checker's annotation
	 * @param theClass
	 *            the class of the checker
	 * @return the instance of specified checker
	 */
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> Checker<A> getCheckerInstance(
			Class<? extends Checker<? extends Annotation>> theClass) {
		try {
			return (Checker<A>) theClass.getConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return EmptyChecker.getInstance();
	}

	private static CheckerAnnotation getWithHighestPriority(
			final List<Annotation> withCheckerAnnotation) {
		Annotation toReturn = withCheckerAnnotation.get(0);
		for (int i = 1; i < withCheckerAnnotation.size(); i++) {
			if (getAnnoPriority(toReturn) < getAnnoPriority(withCheckerAnnotation
					.get(i))) {
				toReturn = withCheckerAnnotation.get(i);
			}
		}
		return toReturn.annotationType().getAnnotation(CheckerAnnotation.class);
	}

	@SuppressWarnings("unchecked")
	private static CheckerAnnotation testForCheckerAnno(
			Collection<Class<? extends Annotation>> toTest,
			final List<Class<? extends Annotation>> excludeList) {
		for (Class<? extends Annotation> a : toTest) {
			CheckerAnnotation dann = getCheckerAnno(a, (Class[]) excludeList
					.toArray(new Class[excludeList.size()]));
			if (dann != null) {
				return dann;
			}
		}
		return null;
	}
	
	private Class<? extends Annotation> annotationType;
	
	private final Collection<ElementType> canAdapt;

	private final Checker<?> checker;
	
	private final int minToFail;

	private final int minToPass;

	@SuppressWarnings("unchecked")
	private final Collection<Class<? extends Checker>> suppressed;

	private CheckerDescriptor(Annotation annotation) {
		this.annotationType = annotation.annotationType();
		
		this.checker = initChecker();
		this.canAdapt = ImmutableList.copyOf(initTargetElements());
		this.suppressed = ImmutableList.copyOf(Arrays.asList(initSuppressCheckers()));
		this.minToFail = initMinAdapterPriorityToFail();
		this.minToPass = initMinAdapterPriorityToPass();
	}
	
	@SuppressWarnings("unchecked")
	private CheckerDescriptor(Class<? extends Checker> checkerClass) {
		this.annotationType = null;
		
		this.checker = getCheckerInstance((Class<? extends Checker<? extends Annotation>>) checkerClass);
		this.canAdapt = NULL_DESCRIPTOR.getCanAdapt();
		this.suppressed = NULL_DESCRIPTOR.getSuppressed();
		this.minToFail = NULL_DESCRIPTOR.getMinToFail();
		this.minToPass = NULL_DESCRIPTOR.getMinToFail();
	}
	
	public boolean canAdapt(AnnotatedElement element) {
		if (element instanceof Class<?>) {
			return canAdapt.contains(ElementType.TYPE);
		}
		if (element instanceof Method) {
			return canAdapt.contains(ElementType.METHOD);
		}
		if (element instanceof Constructor<?>) {
			return canAdapt.contains(ElementType.CONSTRUCTOR);
		}
		if (element instanceof Field) {
			return canAdapt.contains(ElementType.FIELD);
		}
		return false;
	}
	
	public Collection<ElementType> getCanAdapt() {
		return canAdapt;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Annotation> Checker<T> getChecker() {
		return (Checker<T>) checker;
	}
	
	@SuppressWarnings("unchecked")
	private CheckerAnnotation getCheckerAnnotation() {
		return getCheckerAnno(annotationType, Target.class,
				Retention.class, Documented.class, Inherited.class);
	}
	
	public int getMinToFail() {
		return minToFail;
	}
	
	public int getMinToPass() {
		return minToPass;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Class<? extends Checker>> getSuppressed() {
		return suppressed;
	}
	
	private <A extends Annotation> Checker<A> initChecker() {
		final CheckerAnnotation checkerAnnotation = getCheckerAnnotation();
		if (checkerAnnotation == null) {
			return EmptyChecker.getInstance();
		}
		Checker<A> ch = getCheckerInstance(checkerAnnotation.value());
		return ch;
	}
	
	private int initMinAdapterPriorityToFail() {
		if (!annotationType.isAnnotationPresent(MinToFail.class)) {
			return InvocationAdaptersPriorities.NONE;
		}
		return annotationType.getAnnotation(MinToFail.class).value();
	}

	private int initMinAdapterPriorityToPass() {
		if (!annotationType.isAnnotationPresent(MinToPass.class)) {
			return InvocationAdaptersPriorities.METHOD;
		}
		return annotationType.getAnnotation(MinToPass.class).value();
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Checker>[] initSuppressCheckers() {
		if (!annotationType.isAnnotationPresent(SuppressChecker.class)) {
			return (Class<? extends Checker>[]) new Class[0];
		}
		return annotationType.getAnnotation(SuppressChecker.class).value();
	}

	private Collection<ElementType> initTargetElements() {
		if (!annotationType.isAnnotationPresent(CanCheck.class)) {
			return Arrays.asList(CanCheck.DEFAULTS);
		}
		Collection<ElementType> targets = Arrays.asList(annotationType
				.getAnnotation(CanCheck.class).value());
		return targets;
	}
	
	public boolean isValid(){
		return !EmptyChecker.getInstance().equals(checker);
	}
	
	

}
