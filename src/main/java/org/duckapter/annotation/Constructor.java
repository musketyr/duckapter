package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.adapter.InvocationAdaptersPriorities;
import org.duckapter.checker.ConstructorChecker;
import org.duckapter.checker.MethodsOnlyChecker;
import org.duckapter.checker.NameChecker;

/**
 * Declares that the target element must be a constructor. Can be only used on
 * the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@CanCheck({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@CheckerAnnotation(ConstructorChecker.class)
@SuppressChecker({MethodsOnlyChecker.class, NameChecker.class})


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@MinToPass(InvocationAdaptersPriorities.CONSTRUCTOR)
public @interface Constructor {
}
