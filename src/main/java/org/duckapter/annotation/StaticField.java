/**
 * 
 */
package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shortcut for {@link Static} {@link Field} annotations' pair.
 * @author Vladimir Orany
 *
 */
@Documented
@StereotypeChecker
@Static @Field
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.FIELD, ElementType.METHOD })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
public @interface StaticField {
}