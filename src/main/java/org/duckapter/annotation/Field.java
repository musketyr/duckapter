package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.adapter.InvocationAdaptersPriorities;
import org.duckapter.checker.FieldChecker;
import org.duckapter.checker.MethodsOnlyChecker;

/**
 * Denotes that the target element must be a field. Can be only used on methods.
 * 
 * @author Vladimir Orany
 * 
 */
@CanCheck({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@CheckerAnnotation(FieldChecker.class)
@SuppressChecker(MethodsOnlyChecker.class)

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@MinToPass(InvocationAdaptersPriorities.FIELD)
public @interface Field {

}
