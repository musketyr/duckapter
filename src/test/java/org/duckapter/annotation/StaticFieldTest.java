package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.duckapter.Duck;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class StaticFieldTest {

	public static interface SFI {
		@StaticField String text();
	}
	
	public static interface SFC {
		public static String text = "bla";
	}
	
	public static interface SFCF {
		public String text = "bla";
	}
	
	@Test @Ignore
	public void test() {
		assertCanAdaptClass(SFI.class, SFC.class, SFCF.class);
		assertCanAdaptInstance(SFI.class, SFC.class, SFCF.class);
		Assert.assertEquals("bla", Duck.type(SFC.class, SFI.class).text());
	}
}
