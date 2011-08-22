package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.duckapter.InvocationAdapter;
import org.duckapter.adapter.AllAdapter;
import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.annotation.All;

/**
 * Checker for the {@link All} annotation. Suppresses {@link ReturnTypeChecker},
 * {@link ParametersChecker} and {@link ExceptionsChecker}.
 * 
 * @author Vladimir Orany
 * @see All
 */
public class AllChecker extends AbstractChecker<All> {

	@SuppressWarnings("unchecked")
	private static final List<Class<? extends LogicalCheckerBase>> METHOD_CHECKERS_CLASSES = Arrays
			.asList(ReturnTypeChecker.class, ParametersChecker.class,
					ExceptionsChecker.class);
	@SuppressWarnings("unchecked")
	private static final Collection<LogicalCheckerBase> METHOD_CHECKERS;

	static {
		@SuppressWarnings("unchecked")
		Collection<LogicalCheckerBase> checkers = new ArrayList<LogicalCheckerBase>();
		for (@SuppressWarnings("unchecked")
		Class<? extends LogicalCheckerBase> checkerClass : METHOD_CHECKERS_CLASSES) {
			try {
				checkers.add(checkerClass.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		METHOD_CHECKERS = Collections.unmodifiableCollection(checkers);

	}

	@SuppressWarnings("unchecked")
	public InvocationAdapter adapt(All anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		if (original instanceof Class) {
			return InvocationAdapters.OK;
		}
		if (duck instanceof Method) {
			Method duckMethod = (Method) duck;
			return checkReturnType(anno, original, classOfOriginal, duckMethod
					.getReturnType());

		}
		return InvocationAdapters.NULL;
	}

	private InvocationAdapter checkReturnType(All anno,
			AnnotatedElement original, Class<?> classOfOriginal,
			Class<?> retType) {
		if (!retType.isArray()) {
			return InvocationAdapters.NULL;
		}
		return checkMethodsType(anno, original, classOfOriginal,
				(Class<?>) retType.getComponentType());
	}

	private InvocationAdapter checkMethodsType(All anno,
			AnnotatedElement original, Class<?> classOfOriginal,
			Class<?> methodsType) {
		if (!methodsType.isInterface() || methodsType.getMethods().length != 1) {
			return InvocationAdapters.NULL;
		}

		return checkMethodInterface(anno, original, classOfOriginal,
				methodsType.getMethods()[0]);
	}

	private InvocationAdapter checkMethodInterface(All anno,
			AnnotatedElement original, Class<?> classOfOriginal,
			final Method returnTypeOnlyMethod) {
		for (LogicalCheckerBase<Annotation> ch : METHOD_CHECKERS) {
			if (CheckerDescriptor.getDescriptor(anno).canAdapt(original)
					&& InvocationAdapters.isNull(ch.adapt(anno, original,
							returnTypeOnlyMethod, classOfOriginal))) {
				return InvocationAdapters.NULL;
			}
		}
		return new AllAdapter(original, returnTypeOnlyMethod);
	}

}
