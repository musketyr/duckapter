package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.checker.NameChecker;

/**
 * Denotes that the target element can have any name. Can be only used on the
 * duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@StereotypeChecker
@Matching(".*")
@CanCheck({ ElementType.METHOD,	ElementType.CONSTRUCTOR, ElementType.FIELD })
@SuppressChecker(NameChecker.class)

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Any {

}
