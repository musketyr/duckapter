package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.OptionalChecker;

/**
 * Declares that the duck method does not need to find out its counter part. In
 * such a case the duck method returns default value when invoked. Can be only
 * used on the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(OptionalChecker.class)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@MinToFail(Integer.MIN_VALUE)
public @interface Optional {

}
