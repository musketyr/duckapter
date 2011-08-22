package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Inversion of the {@link Static} annotation.
 * @author Vladimir Orany
 * @see Negative
 * @see Static
 */
@Documented
@Negative
@ModifierChecker(Modifier.STATIC)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface NonStatic { } 
