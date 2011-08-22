package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.StereotypeCheckerChecker;
import org.duckapter.checker.StereotypeType;

/**
 * This metaannotation helps creating new checker annotation by composing them.
 * The composition can be conjunct (default) or disjoint. Depending on the
 * {@link #value()} the usage of newly created annotation will be the same as
 * using all annotation used on it - just like creating shortcut - or like 
 * creating multiple choice test where even one passing is good enough to pass
 * - just like {@link Property} or {@link Factory} annotations.
 * 
 * @author Vladimir Orany
 * @see StereotypeType
 */
@Documented
@CheckerAnnotation(StereotypeCheckerChecker.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface StereotypeChecker {

	/**
	 * The checker priority. Do not change this value unless you really know
	 * what are you doing.
	 */
	int checkerPriority() default Integer.MAX_VALUE;

	/**
	 * @see StereotypeType
	 */
	StereotypeType value() default StereotypeType.AND;
}
