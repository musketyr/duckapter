package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.adapter.InvocationAdaptersPriorities;
import org.duckapter.checker.AllChecker;
import org.duckapter.checker.ExceptionsChecker;
import org.duckapter.checker.ParametersChecker;
import org.duckapter.checker.ReturnTypeChecker;

/**
 * The duck method annotated with this annotation gathers all targets elements
 * which succeed the check. All checks from annotations are performed against
 * the duck method besides the default checks for the return type, for the
 * method parameters and for the exceptions. Those are checked against the
 * single method of the interface which is component of the array which is
 * declared as the return type of the duck method. In there are no target
 * elements which succeed an empty array is returned when the annotated method
 * is invoked. Can be used only on the duck methods.
 * 
 * @author Vladimir Orany
 */
@CheckerAnnotation(AllChecker.class)
@CanCheck({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
@SuppressChecker({ReturnTypeChecker.class, ParametersChecker.class,	ExceptionsChecker.class})
@MinToPass(InvocationAdaptersPriorities.MAX)

@Documented
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface All {

}
