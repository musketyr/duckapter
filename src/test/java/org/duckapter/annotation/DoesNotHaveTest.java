package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class DoesNotHaveTest {

	public static interface HasNoInterface {
		@DoesNotHave @Field @Matching(".*") String hasNoField();
	}

	public static class HasField {
		public String field;
	}

	public static class HasNoField {
		public String hasNoField() {
			return null;
		}
	}

	@Test
	public void testHasNoField() {
		assertCanAdaptInstance(HasNoInterface.class, HasNoField.class,
				HasField.class);
	}

}
