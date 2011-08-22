package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class VolatileTest {

	public static interface WithVolatileFieldInterface {
		@Volatile
		@Field
		Object getField();
	}

	public static class WithVolatileField {
		public volatile String field = "";
	}

	public static class WithoutVolatileField {
		public String field = "";
	}

	@Test
	public void testVolatle() throws Exception {
		assertCanAdaptInstance(WithVolatileFieldInterface.class,
				WithVolatileField.class, WithoutVolatileField.class);
	}

}
