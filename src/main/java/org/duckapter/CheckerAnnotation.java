package org.duckapter;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that annotation which is annotated by this metaannotation will be
 * used for checking the elements from original instances and/or classes.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface CheckerAnnotation {

	/**
	 * The class of the checker binded to this annotation.
	 */
	Class<? extends Checker<? extends Annotation>> value();
}
