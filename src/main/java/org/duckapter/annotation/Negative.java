package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.LogicalChecker;
import org.duckapter.checker.NonCheckerChecker;

/**
 * This metaanotation negates the result of the check performed by the sibling
 * annotation placed on the newly created checker annotation. The sibling
 * annotation's checker must implement {@link LogicalChecker} interface. Only
 * one sibling annotation must be present on the newly created annotation.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(NonCheckerChecker.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Negative {
	/**
	 * Checker priority used by {@link StereotypeChecker}. Do not change this
	 * value unless you really know what are you doing.
	 */
	int checkerPriority() default Integer.MAX_VALUE - 5000;
}
