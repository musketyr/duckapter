package org.duckapter.annotation;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class AnyTest {

	public static interface AnyMethod {
		@Any @Declared
		void doIt();
	}

	public static class HasMethod {
		public void someStrangeName() {
		}
	}

	public static class HasNoMethod {
		// nothing
	}

	@Test
	public void testIt() {
		DuckTestHelper.assertCanAdaptInstance(AnyMethod.class, HasMethod.class,
				HasNoMethod.class);
	}

}
