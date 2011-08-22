package org.duckapter.checker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.duckapter.annotation.Matching;

/**
 * Checker for the {@link Matching} annotation. Suppresses {@link NameChecker}.
 * 
 * @author Vladimir Orany
 * @see Matching
 */
public class MatchingChecker extends LogicalCheckerBase<Matching> {

	@Override
	public boolean checkClass(Matching anno, Class<?> clazz,
			Class<?> duckInterface) {
		return preparePattern(anno, duckInterface.getName(), clazz.getName())
				.matcher(clazz.getName()).matches();
	}

	@Override
	protected boolean checkConstructor(Matching anno,
			Constructor<?> constructor, Method duckMethod,
			Class<?> classOfOriginal) {
		return preparePattern(anno, duckMethod.getName(),
				classOfOriginal.getName()).matcher(constructor.getName())
				.matches();
	}

	@Override
	protected boolean checkField(Matching anno, Field field, Method duckMethod,
			Class<?> classOfOriginal) {
		return preparePattern(anno, duckMethod.getName(),
				classOfOriginal.getName()).matcher(field.getName()).matches();
	}

	@Override
	protected boolean checkMethod(Matching anno, Method method,
			Method duckMethod, Class<?> classOfOriginal) {
		return preparePattern(anno, duckMethod.getName(),
				classOfOriginal.getName()).matcher(method.getName()).matches();
	}

	private Pattern preparePattern(Matching anno, String name, String className) {
		int flag = 0;
		if (anno.canonical()) {
			flag |= Pattern.CANON_EQ;
		}
		if (anno.caseInsensitive()) {
			flag |= Pattern.CASE_INSENSITIVE;
		}
		if (anno.comments()) {
			flag |= Pattern.COMMENTS;
		}
		if (anno.dotAllMode()) {
			flag |= Pattern.DOTALL;
		}
		if (anno.literal()) {
			flag |= Pattern.LITERAL;
		}
		if (anno.multiLine()) {
			flag |= Pattern.MULTILINE;
		}
		if (anno.unicodeCase()) {
			flag |= Pattern.UNICODE_CASE;
		}
		if (anno.unixLines()) {
			flag |= Pattern.UNIX_LINES;
		}
		String value = anno.value().replace("${name}", name).replace(
				"${className}", className);

		return Pattern.compile(value, flag);
	}

}
