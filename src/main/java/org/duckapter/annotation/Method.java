package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.MethodChecker;

/**
 * Denotes that the target element must be a method. Usually is this annotation
 * used as a "syntactic sugar" because the target elements must be methods by
 * default. Can be only used on the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@CheckerAnnotation(MethodChecker.class)
@CanCheck({ ElementType.METHOD })

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface Method {

}
