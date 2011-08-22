package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class TransientTest {

	public static interface WithTransientFieldInterface {
		@Transient
		@Field
		Object getField();
	}

	public static class WithTransientField {
		public transient String field = "";
	}

	public static class WithoutTransientField {
		public String field = "";
	}
	
	public static class WithMethodField {
		public String getField(){ return ""; }
	}

	@Test
	public void testTransient() throws Exception {
		assertCanAdaptInstance(WithTransientFieldInterface.class,
				WithTransientField.class, WithoutTransientField.class);
	}
	
	@Test
	public void testTransientMethod() throws Exception {
		assertCanAdaptInstance(WithTransientFieldInterface.class,
				WithTransientField.class, WithMethodField.class);
	}

}
