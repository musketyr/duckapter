package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.adapter.InvocationAdaptersPriorities;
import org.duckapter.checker.ParametersChecker;
import org.duckapter.checker.WithAnyParamsChecker;

/**
 * Denotes that the target element can have any parameters declared. Once used
 * on the duck method the duck method cannot be called and is used only for
 * discrimination. Can only be used on the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(WithAnyParamsChecker.class)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD,	ElementType.CONSTRUCTOR })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@SuppressChecker(ParametersChecker.class)
@MinToPass(InvocationAdaptersPriorities.WITH_ANY_PARAMS)
public @interface WithAnyParams {

}
