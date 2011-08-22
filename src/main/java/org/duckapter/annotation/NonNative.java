package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Inversion of the {@link Native} annotation.
 * @author Vladimir Orany
 * @see Negative
 * @see Native
 */
@Documented
@Negative
@ModifierChecker(Modifier.NATIVE)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface NonNative {

}
