package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that original class must be annotation type. Can be only used on
 * the duck interface.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@CanCheck({ ElementType.TYPE })
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@AssignableTo(Annotation.class)
public @interface Annotation {

}
