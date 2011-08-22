package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class ConstructorTest {

	public static interface WithConstructorInterface {
		@Constructor
		WithConstructorInterface newInstance();
	}

	public static class WithDefaultConstructor {
		// default constructor lives here
	}

	public static class WithDefinedConstructor {
		public WithDefinedConstructor() {
		}
	}

	public static class WithoutDefinedConstructor {
		private WithoutDefinedConstructor() {
		}
	}

	public static class WithMethod {
		private WithMethod(int integer) {
		}

		public Object newInstance() {
			return null;
		}
	}

	@Test
	public void testConstructor1() throws Exception {
		assertCanAdaptClass(WithConstructorInterface.class,
				WithDefinedConstructor.class, WithoutDefinedConstructor.class);
		assertCanAdaptInstance(WithConstructorInterface.class,
				WithDefinedConstructor.class, WithoutDefinedConstructor.class);

	}
	
	@Test
	public void testConstructor2() throws Exception {
		assertCanAdaptClass(WithConstructorInterface.class,
				WithDefaultConstructor.class, WithoutDefinedConstructor.class);
		assertCanAdaptInstance(WithConstructorInterface.class,
				WithDefaultConstructor.class, WithoutDefinedConstructor.class);
	}
	
	@Test
	public void testConstructor3() throws Exception {
		assertCanAdaptClass(WithConstructorInterface.class,
				WithDefinedConstructor.class, WithMethod.class);
		assertCanAdaptInstance(WithConstructorInterface.class,
				WithDefinedConstructor.class, WithMethod.class);
	}

}
