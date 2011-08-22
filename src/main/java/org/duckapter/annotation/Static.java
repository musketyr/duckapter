package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * The substitute for the <code>static</code> keyword. Can be used on the duck
 * interface or the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@ModifierChecker(Modifier.STATIC)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface Static { } 
