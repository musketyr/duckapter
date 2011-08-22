package org.duckapter.adapted;

import static org.duckapter.adapted.AdaptedClassHelper.getRelevantElements;
import static org.duckapter.adapter.InvocationAdapters.MAX;
import static org.duckapter.adapter.InvocationAdapters.MIN;
import static org.duckapter.adapter.InvocationAdapters.safe;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.duckapter.Checker;
import org.duckapter.InvocationAdapter;
import org.duckapter.annotation.CanCheck;
import org.duckapter.checker.CheckerDescriptor;

import com.google.common.collect.ImmutableMap;

final class DuckElementWrapper {

	private final AnnotatedElement element;
	private final Map<ElementType, Map<Checker, Annotation>> checkers;
	private final int minToPass;
	private final int minToFail;
	private final ElementType[] testedElements;
	
	public DuckElementWrapper(AnnotatedElement method,
			Map<ElementType, Map<Checker, Annotation>> checkers, int minToPass,
			int minToFail) {
		this.testedElements = initTestedElements(method);
		this.element = method;
		this.checkers = ImmutableMap.copyOf(checkers);
		this.minToPass = minToPass;
		this.minToFail = minToFail;
	}

	private ElementType[] initTestedElements(AnnotatedElement element){
		if (element instanceof Class<?>) {
			return new ElementType[]{ElementType.TYPE};
		}
		if (element instanceof Method) {
			return new ElementType[]{ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD};
		}
		return CanCheck.DEFAULTS;
	}
	
	public AnnotatedElement getElement() {
		return element;
	}

	public Map<ElementType, Map<Checker, Annotation>> getCheckers() {
		return checkers;
	}

	public int getMinToPass() {
		return minToPass;
	}

	public int getMinToFail() {
		return minToFail;
	}

	public InvocationAdapter resolveAdapter(Map<Method, InvocationAdapter> builder, Class<?> originalClass) {
		InvocationAdapter ret = initialForDuckMethod(builder);
		for (ElementType elType : testedElements) {
			for (AnnotatedElement tested : getRelevantElements(elType,originalClass)) {
				final InvocationAdapter adapter = resolveAdapter(tested, builder, elType, originalClass);
				ret = mergeAdaptersFromElements(ret, adapter);
				if (ret.getPriority() >= minToPass) {
					return ret;
				}
			}
		}
		return ret;
	}

	private InvocationAdapter initialForDuckMethod(Map<Method, InvocationAdapter> builder) {
		return safe(builder.get(element), MIN);
	}


	private final InvocationAdapter resolveAdapter(AnnotatedElement original, Map<Method, InvocationAdapter> builder, ElementType curType, Class<?> originalClass) {
		InvocationAdapter ret = initialForElement(builder);
		Map<Checker, Annotation> tested = checkers.get(curType);
		for (Entry<Checker, Annotation> entry : tested.entrySet()) {
			final Checker ch = entry.getKey();
			final Annotation anno = entry.getValue();
			if (CheckerDescriptor.getDescriptor(anno).canAdapt(original)) {
				final InvocationAdapter adapter = ch.adapt(anno, original,	element, originalClass);
				ret = mergeAdaptersFromCheckers(ret, adapter);
				if (ret.getPriority() <= minToFail) {
					return ret;
				}
			}
		}
		return ret;
	}

	private InvocationAdapter initialForElement(Map<Method, InvocationAdapter> builder) {
		return safe(builder.get(element), MAX);
	}

	private InvocationAdapter mergeAdaptersFromElements(InvocationAdapter ret, final InvocationAdapter adapter) {
		if (ret.getPriority() >= adapter.getPriority()) {
			return ret.orMerge(adapter);
		}
		return adapter.orMerge(ret);
	}

	private InvocationAdapter mergeAdaptersFromCheckers(InvocationAdapter ret, final InvocationAdapter adapter) {
		if (ret.getPriority() >= adapter.getPriority()) {
			return ret.andMerge(adapter);
		}
		return adapter.andMerge(ret);
	}

}
