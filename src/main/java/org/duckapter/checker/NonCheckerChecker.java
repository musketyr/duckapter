package org.duckapter.checker;

import static org.duckapter.adapter.InvocationAdapters.NULL;
import static org.duckapter.adapter.InvocationAdapters.OK;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.duckapter.Checker;
import org.duckapter.InvocationAdapter;
import org.duckapter.LogicalChecker;
import org.duckapter.annotation.Negative;

/**
 * Checker for the {@link Negative} metaannotation.
 * 
 * @author Vladimir Orany
 * @see Negative
 */
public class NonCheckerChecker extends AbstractChecker<Annotation> implements
		LogicalChecker<Annotation> {

	@SuppressWarnings("unchecked")
	public boolean check(Annotation anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		Class<?> annoType = anno.annotationType();
		for (Annotation a : annoType.getAnnotations()) {
			if (Negative.class.equals(a.annotationType())) {
				continue;
			}
			CheckerDescriptor descriptor = CheckerDescriptor.getDescriptor(a);
			if (descriptor.isValid()) {
				final Checker<Annotation> checker = descriptor.getChecker();
				if (checker instanceof LogicalChecker) {
					LogicalChecker logical = (LogicalChecker) checker;
					return !logical
							.check(anno, original, duck, classOfOriginal);
				}
			}
		}
		throw new IllegalArgumentException("Annotation " + anno
				+ " is annotated with @Negative "
				+ "but has no LogicalChecker annotation present!");

	}

	public InvocationAdapter adapt(Annotation anno, AnnotatedElement original,
			AnnotatedElement duck, Class<?> classOfOriginal) {
		return check(anno, original, duck, classOfOriginal) ? OK : NULL;
	}

}
