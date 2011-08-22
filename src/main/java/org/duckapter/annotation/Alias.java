package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.AliasChecker;
import org.duckapter.checker.NameChecker;

/**
 * Allows the target element to have more alternative potential names. Can be
 * used only on the duck methods. Does not provide any guarantee which target
 * element will be picked if more than one succeed the check.
 * 
 * @author Vladimir Orany
 * 
 */
@Documented
@CheckerAnnotation(AliasChecker.class)
@CanCheck({ ElementType.METHOD,	/*ElementType.CONSTRUCTOR,*/ ElementType.FIELD })
@SuppressChecker(NameChecker.class)

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface Alias {

	/**
	 * Array of other potential names of the target element.
	 */
	String[] value();

}
