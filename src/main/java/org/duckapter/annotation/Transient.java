package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * The substitute for the <code>transient</code> keyword. Can be only used on
 * the duck method. The target element must be a field.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@ModifierChecker(Modifier.TRANSIENT)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.FIELD, ElementType.METHOD })
@Target({ ElementType.ANNOTATION_TYPE,  ElementType.METHOD })
public @interface Transient {

}
