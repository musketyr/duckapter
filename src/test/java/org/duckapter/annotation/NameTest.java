package org.duckapter.annotation;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class NameTest {

	public static interface NameInterface {
		void name();
	}

	public static class NameClass {
		public void name() {
		}
	}

	public static class OtherNameClass {
		public void otherName() {
		}
	}
	
	public static class Name2Class {
		public void NA_ME() {
		}
	}

	@Test
	public void testName() {
		DuckTestHelper.assertCanAdaptInstance(NameInterface.class,
				NameClass.class, OtherNameClass.class);
	}
	
	@Test
	public void testName2() {
		DuckTestHelper.assertCanAdaptInstance(NameInterface.class,
				Name2Class.class, OtherNameClass.class);
	}

}
