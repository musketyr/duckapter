package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class NativeTest {

	public static interface NativeInterface {
		@Native
		void doIt();
	}

	public static class WithNative {
		public native void doIt();
	}

	public static class WithoutNative {
		public void doIt() {
		}
	}

	@Test
	public void testNative() {
		assertCanAdaptInstance(NativeInterface.class, WithNative.class,
				WithoutNative.class);
	}

}
