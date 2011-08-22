package org.duckapter.checker;

import java.lang.reflect.AnnotatedElement;

import org.duckapter.adapter.InvocationAdapters;
import org.duckapter.annotation.WithAnyParams;

/**
 * Checker for the {@link WithAnyParams} annotation. Suppresses
 * {@link ParametersChecker}.
 * 
 * @author Vladimir Orany
 * @see WithAnyParams
 */
public class WithAnyParamsChecker extends AbstractChecker<WithAnyParams> {

	public org.duckapter.InvocationAdapter adapt(WithAnyParams anno,
			AnnotatedElement original, AnnotatedElement duck,
			Class<?> classOfOriginal) {
		return InvocationAdapters.DISCRIMINATOR;
	};
	
}
