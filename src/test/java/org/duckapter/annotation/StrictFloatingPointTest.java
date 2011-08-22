package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class StrictFloatingPointTest {

	public static interface WithStrictMethodInterface {
		@StrictFloatingPoint
		void doIt();
	}

	public static class WithStrictMethodClass {
		public strictfp void doIt() {
		}
	}

	public static class WithoutStrictMethodClass {
		public void doIt() {
		}
	}

	@Test
	public void testStrict() throws Exception {
		assertCanAdaptInstance(WithStrictMethodInterface.class,
				WithStrictMethodClass.class, WithoutStrictMethodClass.class);
	}

}
