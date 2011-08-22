package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.PublicOnlyChecker;
import org.duckapter.checker.Visibility;
import org.duckapter.checker.VisibilityChecker;

/**
 * Denotes that the target elements must declare protected visibility. The
 * desired visibility can be more flexible using the {@link #value()}. Can be
 * used on the duck interfaces and on the duck methods.
 * 
 * @author Vladimir Orany
 * 
 */
@CanCheck({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR})
@CheckerAnnotation(VisibilityChecker.class)
@SuppressChecker(PublicOnlyChecker.class)

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface Protected {
	
	/**
	 * The visibility matching mode.
	 * @see Visibility
	 */
	Visibility value() default Visibility.EXACT;
}
