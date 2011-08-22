package org.duckapter.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.duckapter.checker.StereotypeType.OR;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.duckapter.checker.MethodsOnlyChecker;
import org.duckapter.checker.NameChecker;

/**
 * Declares that the target element must be a constructor, a static method or a
 * static field. Can be only used on the duck method.
 * @author Vladimir Orany
 * 
 */
@StereotypeChecker(OR)
@Retention(RUNTIME)
@CanCheck({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@SuppressChecker({MethodsOnlyChecker.class, NameChecker.class})

@Documented
@Constructor
@StaticField
@StaticMethod
public @interface Factory {
}
