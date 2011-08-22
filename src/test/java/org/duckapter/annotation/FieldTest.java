package org.duckapter.annotation;

import org.duckapter.Duck;
import org.duckapter.DuckTestHelper;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {

	public static interface FieldInterface {
		@Field
		String getField();

		@Field
		void setField(String field);
	}

	public static interface FieldInterface2 {
		@Field
		String field();

		@Field
		void field(String field);
	}

	public static class WithField {
		public String field = "field";
	}

	public static class WithFinalField {
		public final String field = "field";
	}

	public static class WithMethods {
		public String getField() {
			return "fake";
		};

		public void setField(String field) {
		};
	}

	public static class WithMethods2 {
		public String field() {
			return "fake";
		};

		public void field(String field) {
		};
	}

	@Test
	public void testField() {
		DuckTestHelper.assertCanAdaptInstance(FieldInterface.class,
				WithField.class, WithMethods.class);
		DuckTestHelper.assertCanAdaptInstance(FieldInterface.class,
				WithField.class, WithFinalField.class);
		FieldInterface field = Duck.type(new WithField(),
				FieldInterface.class);
		Assert.assertEquals("field", field.getField());
		field.setField("newValue");
		Assert.assertEquals("newValue", field.getField());
	}

	@Test
	public void testField2() {
		DuckTestHelper.assertCanAdaptInstance(FieldInterface2.class,
				WithField.class, WithMethods2.class);
		FieldInterface2 field = Duck.type(new WithField(),
				FieldInterface2.class);
		Assert.assertEquals("field", field.field());
		field.field("newValue");
		Assert.assertEquals("newValue", field.field());
	}

}
