package org.duckapter.annotation;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class MethodsOnlyTest {

	public static interface MOI {
		Object test();
	}

	public static class WithMethod {
		public Object test() {
			return null;
		}
	}

	public static class test {
		public test() {
		}
	}

	public static class WithField {
		public Object test;
	}

	@Test
	public void testMethodsOnly() {
		DuckTestHelper.assertCanAdaptInstance(MOI.class, WithMethod.class,
				test.class);
		DuckTestHelper.assertCanAdaptInstance(MOI.class, WithMethod.class,
				WithField.class);
	}

}
