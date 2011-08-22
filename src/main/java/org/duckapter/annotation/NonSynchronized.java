package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Inversion of the {@link Synchronized} annotation.
 * @author Vladimir Orany
 * @see Negative
 * @see Synchronized
 */
@Documented
@Negative
@ModifierChecker(Modifier.SYNCHRONIZED)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
public @interface NonSynchronized {

}
