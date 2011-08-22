package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.duckapter.Duck;
import org.junit.Assert;
import org.junit.Test;

public class PropertyTest {

	public static interface PropertyInterface {
		@Property
		String getProperty();

		@Property
		void setProperty(String property);
	}

	public static class WithFieldProperty {
		public String property = "property";
	}

	public static class WithFinalProperty {
		public final String property = "property";
	}

	public static class WithMethodProperty {
		private String prop = "property";

		public String getProperty() {
			return prop;
		}

		public void setProperty(String str) {
			prop = str;
		}
	}

	@Test
	public void testProperty() {
		assertCanAdaptInstance(PropertyInterface.class,
				WithFieldProperty.class, WithFinalProperty.class);

	}

	@Test
	public void testMethodProps() {
		assertProps(Duck.type(new WithMethodProperty(),
				PropertyInterface.class));
	}

	@Test
	public void testMethod() {
		assertCanAdaptInstance(PropertyInterface.class,
				WithMethodProperty.class, WithFinalProperty.class);
	}

	@Test
	public void testFieldProps() {
		assertProps(Duck.type(new WithFieldProperty(),
				PropertyInterface.class));
	}

	private void assertProps(PropertyInterface pi) {
		Assert.assertEquals("property", pi.getProperty());
		pi.setProperty("newProp");
		Assert.assertEquals("newProp", pi.getProperty());
	}

}
