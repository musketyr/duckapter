package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class StaticTest {

	public static interface WithStaticMethodInterface {
		@Static
		void doIt();
	}

	public static class WithStaticMethodClass {
		public static void doIt() {
		}
	}

	public static class WithoutStaticMethodClass {
		public void doIt() {
		}
	}

	@Test
	public void testStatic() throws Exception {
		assertCanAdaptClass(WithStaticMethodInterface.class,
				WithStaticMethodClass.class, WithoutStaticMethodClass.class);
		assertCanAdaptInstance(WithStaticMethodInterface.class,
				WithStaticMethodClass.class, WithoutStaticMethodClass.class);
	}
	
	@Static public static interface StaticInterface{}
	
	public static class StaticClass{}
	
	public class MemberClass{}
	
	@Test
	public void testStaticClass() throws Exception {
		assertCanAdaptClass(StaticInterface.class,
				StaticClass.class, MemberClass.class);
		assertCanAdaptInstance(StaticInterface.class,
				StaticClass.class, MemberClass.class);
	}

}
