package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Inversion of the {@link Abstract} annotation.
 * @author Vladimir Orany
 * @see Negative
 * @see Abstract
 */
@Documented
@Negative
@ModifierChecker(Modifier.ABSTRACT)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD, ElementType.TYPE })
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface NonAbstract {

}
