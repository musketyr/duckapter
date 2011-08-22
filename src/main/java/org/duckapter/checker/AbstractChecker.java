package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;

import org.duckapter.Checker;
import org.duckapter.annotation.CanCheck;

/**
 * The base class for implementation of the {@link Checker} interface. It
 * provides simplified {@link #canAdapt(Annotation, AnnotatedElement, Class)}
 * method which is based on reading checker annotation {@link Target}
 * annotation. The targets are cached. The {@link #hashCode()} method is
 * optimalized and the equals method delegates the
 * {@link Checkers#equals(Object)} method.
 * 
 * @author Vladimir Orany
 * 
 * @param <T>
 *            the annotation bind to the checker
 */
public abstract class AbstractChecker<T extends Annotation> implements
		Checker<T> {

	/**
	 * The list of all possible targets. Returning this value from the
	 * {@link #getTargetElements(Annotation)} method will result in allowing any
	 * element to be adapted.
	 */
	protected static final List<ElementType> ALL_TARGETS = Arrays
			.asList(CanCheck.DEFAULTS);
	private final int hashCode;

	/**
	 * Creates new instance of this class.
	 */
	public AbstractChecker() {
		this.hashCode = Checkers.hashCode(getClass());
	}

	
	@Override
	public final boolean equals(Object obj) {
		return Checkers.equals(this, obj);
	}

	@Override
	public final int hashCode() {
		return hashCode;
	}

}