package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Inversion of the {@link StrictFloatingPoint} annotation.
 * 
 * @author Vladimir Orany
 * @see Negative
 * @see StrictFloatingPoint
 */
@Documented
@Negative
@ModifierChecker(Modifier.STRICT)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ElementType.METHOD, ElementType.TYPE })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE })
public @interface NonStrictFloatingPoint {

}
