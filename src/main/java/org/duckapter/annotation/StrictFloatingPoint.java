package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
/**
 * The substitute for the <code>stictfp</code> keyword. Can be used on the duck
 * interface or the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@ModifierChecker(Modifier.STRICT)
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ElementType.METHOD, ElementType.TYPE })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE })
public @interface StrictFloatingPoint {

}
