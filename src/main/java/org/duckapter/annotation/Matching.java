package org.duckapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.checker.MatchingChecker;
import org.duckapter.checker.NameChecker;

/**
 * The annotation suppress default name check and provide matching based on the
 * regular expression specified as the {@link #value()}. All common flags can be
 * set using the particular attribute. The name of the duck method does not
 * affect the matching in any way. Can be only used on the duck method.
 * 
 * @author Vladimir Orany
 * 
 */
@CheckerAnnotation(MatchingChecker.class)
@CanCheck({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE })
@SuppressChecker(NameChecker.class)

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface Matching {
	/**
	 * The regular expression for matching target elements' name.
	 */
	String value();

	/**
	 * @see java.util.regex.Pattern#CASE_INSENSITIVE
	 */
	boolean caseInsensitive() default false;

	/**
	 * @see java.util.regex.Pattern#DOTALL
	 */
	boolean dotAllMode() default false;

	/**
	 * @see java.util.regex.Pattern#UNIX_LINES
	 */
	boolean unixLines() default false;

	/**
	 * @see java.util.regex.Pattern#COMMENTS
	 */
	boolean comments() default false;

	/**
	 * @see java.util.regex.Pattern#MULTILINE
	 */
	boolean multiLine() default false;

	/**
	 * @see java.util.regex.Pattern#LITERAL
	 */
	boolean literal() default false;

	/**
	 * @see java.util.regex.Pattern#UNICODE_CASE
	 */
	boolean unicodeCase() default false;

	/**
	 * @see java.util.regex.Pattern#CANON_EQ
	 */
	boolean canonical() default false;
}
