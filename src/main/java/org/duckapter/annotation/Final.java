package org.duckapter.annotation;

import static java.lang.reflect.Modifier.FINAL;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The substitute for the <code>final</code> keyword. Can be used on the duck
 * interface or the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ModifierChecker(FINAL)
@CanCheck({ ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface Final {

}
