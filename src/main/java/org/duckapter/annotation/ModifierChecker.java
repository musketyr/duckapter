package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.ModifierCheckerChecker;

/**
 * This metaanotation helps creating new checker annotation used for checking by
 * target elements' modifiers. Just specify the desired mask as the
 * {@link #value()}.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(ModifierCheckerChecker.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ModifierChecker {
	/**
	 * Checker priority used by {@link StereotypeChecker}. Do not change this
	 * value unless you really know what are you doing.
	 */
	int checkerPriority() default Integer.MAX_VALUE - 10000;

	/**
	 * The mask used matching the target elements' modifiers.
	 * @see java.lang.reflect.Modifier
	 */
	int value();
}
