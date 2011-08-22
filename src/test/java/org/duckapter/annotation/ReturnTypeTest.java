package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class ReturnTypeTest {

	public static interface VoidRTInterface {
		void doIt();
	}

	public static interface StringRTInterface {
		String doIt();
	}

	public static interface DuckRTInterface {
		VoidRTInterface doIt();
	}

	public static class Dummy {
	}

	public static class VoidRTClass {
		public void doIt() {
		}
	}

	public static class StringRTClass {
		public String doIt() {
			return "doIt";
		}
	}

	public static class DuckRTClass {
		public VoidRTClass doIT() {
			return new VoidRTClass();
		}
	}

	@Test
	public void testRT() {
		assertCanAdaptInstance(VoidRTInterface.class, VoidRTClass.class,
				Dummy.class);
		assertCanAdaptInstance(VoidRTInterface.class, StringRTClass.class,
				Dummy.class);
		assertCanAdaptInstance(VoidRTInterface.class, DuckRTClass.class,
				Dummy.class);
		assertCanAdaptInstance(StringRTInterface.class, StringRTClass.class,
				VoidRTClass.class);
		assertCanAdaptInstance(StringRTInterface.class, StringRTClass.class,
				DuckRTClass.class);
		assertCanAdaptInstance(DuckRTInterface.class, DuckRTClass.class,
				VoidRTClass.class);
		assertCanAdaptInstance(DuckRTInterface.class, DuckRTClass.class,
				StringRTClass.class);

	}

}
