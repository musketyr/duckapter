package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class StaticMethodTest {

	public static interface SMI {
		@StaticMethod
		void doIt();
	}

	public static class SMC {
		public static void doIt() {
		};
	}

	public static class SMCF {
		public void doIt() {
		};
	}

	@Test
	public void test() {
		assertCanAdaptClass(SMI.class, SMC.class, SMCF.class);
		assertCanAdaptInstance(SMI.class, SMC.class, SMCF.class);
	}

}
