package org.duckapter.annotation;

import static java.lang.reflect.Modifier.FINAL;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inversion of the {@link Final} annotation.
 * @author Vladimir Orany
 * @see Negative
 * @see Final
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Negative
@ModifierChecker(FINAL)
@CanCheck({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD,
		ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface NonFinal {

}
