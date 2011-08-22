package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.AssignableToChecker;

/**
 * Denotes that the original class must be assignable to the classes specified
 * by the {@link #value()}. Can be only used on the duck interface.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@CheckerAnnotation(AssignableToChecker.class)
@CanCheck({ ElementType.TYPE})
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface AssignableTo {
	/**
	 * Classes which the original class must be assignable to.
	 */
	Class<?>[] value();
}
