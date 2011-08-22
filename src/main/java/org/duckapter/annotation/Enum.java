package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that the original class must be enum. Can be only used on the duck
 * method.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.TYPE })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@AssignableTo(Enum.class)
public @interface Enum {

}
