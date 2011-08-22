package org.duckapter.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class NonCheckerTest {

	@Negative
	@ModifierChecker(Modifier.FINAL)
	@Documented
	@Retention(RUNTIME)
	@Target( { METHOD, ANNOTATION_TYPE })
	public static @interface MyNonFinal {
	}

	public static interface MNF {
		@MyNonFinal
		void doIt();
	}

	public static class MNFP {
		public void doIt() {
		}
	}

	public static class MNFF {
		public final void doIt() {
		}
	}

	@Test public void test() {
		assertCanAdaptInstance(MNF.class, MNFP.class, MNFF.class);
	}
}
