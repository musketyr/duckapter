/**
 * 
 */
package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Shortcut for {@link Static} {@link Method} annotations' pair.
 * @author Vladimir Orany
 *
 */
@Documented
@ModifierChecker(Modifier.STATIC)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
public @interface StaticMethod {
}