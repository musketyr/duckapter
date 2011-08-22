package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class DeclaredTest {

	public static interface DeclaredInterface {
		@Declared int hashCode();
	}

	public static class WithDeclared {
		public int hashCode() {
			return super.hashCode();
		}
	}

	public static class WihtoutDeclared {
		// hashCode from Object lives here
	}

	@Test
	public void testDeclared() {
		assertCanAdaptInstance(DeclaredInterface.class,
				WithDeclared.class, WihtoutDeclared.class);
	}

}
