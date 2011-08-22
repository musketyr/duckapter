package org.duckapter;

import static org.junit.Assert.assertEquals;

import org.duckapter.annotation.Alias;
import org.duckapter.annotation.Factory;
import org.duckapter.annotation.Property;
import org.duckapter.annotation.Static;
import org.junit.Test;

public class PropertyUnitTest {

	public interface PropertyTest {
		@Property()
		@Alias("intField")
		int getInt();

		@Property()
		@Alias("intField")
		void setInt(int i);

		@Property()
		@Alias("prop")
		String getSomeProperty();

		@Property()
		@Alias("prop")
		void setSomeProperty(String s);
	}
	
	public static interface PropertyTestStatic {

		@Factory
		public abstract PropertyTest instantiate();

		@Static
		@Property
		@Alias("staticStringField")
		public abstract String getString();

		@Static
		@Property
		@Alias("staticStringField")
		public abstract void setString(String s);

	}
	
	public static class PropertyTestClass {

		public static String staticStringField;

		public int intField;

		private static boolean bool;

		private String something;

		public String getProp() {
			return something;
		}

		public void setProp(String s) {
			something = s;
		}

		public static boolean getBool() {
			return bool;
		}

		public static void setBool(boolean bool) {
			PropertyTestClass.bool = bool;
		}

	}
	
	
	@Test
	public void testProperty() throws Exception {
		PropertyTestStatic propStatic = Duck.type(
				PropertyTestClass.class, PropertyTestStatic.class);
		propStatic.setString("Test");
		assertEquals("Test", propStatic.getString());
	}
	
	@Test
	public void testInstance(){
		PropertyTest fixure = Duck.type(new PropertyTestClass(),
				PropertyTest.class);
		fixure.setInt(10);
		assertEquals(10, fixure.getInt());
	}

}
