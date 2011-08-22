package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class PublicOnlyTest {

	public static interface PublicOnly {
		void doIt();
	}

	public static class PublicMethodClass {
		public void doIt() {
		}
	}

	public static class ProtectedMethodClass {
		void doIt() {
		}
	}

	@Test
	public void testPublicOnly() {
		assertCanAdaptInstance(PublicOnly.class, PublicMethodClass.class,
				ProtectedMethodClass.class);
	}
	
	

}
