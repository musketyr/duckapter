package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * The substitute for the <code>native</code> keyword. Can be only used on duck
 * method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@ModifierChecker(Modifier.NATIVE)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.METHOD })
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface Native {

}
