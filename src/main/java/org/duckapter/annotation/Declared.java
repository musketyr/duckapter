package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.DeclaredChecker;

/**
 * Denotes that the target element must be declared in the original class not
 * and not in its superclases. Can be only used on the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(DeclaredChecker.class)
@CanCheck({ElementType.METHOD, ElementType.FIELD})
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Declared {

}
