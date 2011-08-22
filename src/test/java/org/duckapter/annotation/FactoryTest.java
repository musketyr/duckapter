package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;
import junit.framework.Assert;

import org.duckapter.Duck;
import org.junit.Test;

public class FactoryTest {

	public static interface FactoryInterface {
		@Factory
		Object getInstance();
	}

	public static class WithConstructor {
		public WithConstructor() {
		}
	}

	public static class WithFactory {
		public static Object getInstance() {
			return new WithFactory();
		}
	}

	public static class WithField {
		public static Object INSTANCE = new WithField();
	}

	public static class WithoutAnyFactoryMethod {
		private WithoutAnyFactoryMethod(int integer) {
		}
	}

	@Test
	public void testFactoryConstructor() {
		assertCanAdaptClass(FactoryInterface.class, WithConstructor.class,
				WithoutAnyFactoryMethod.class);
		assertCanAdaptInstance(FactoryInterface.class, WithConstructor.class,
				WithoutAnyFactoryMethod.class);

	}

	@Test
	public void testFactoryMethod() {

		assertCanAdaptClass(FactoryInterface.class, WithFactory.class,
				WithoutAnyFactoryMethod.class);
		assertCanAdaptInstance(FactoryInterface.class, WithFactory.class,
				WithoutAnyFactoryMethod.class);

	}

	@Test
	public void testFactoryField() {

		assertCanAdaptClass(FactoryInterface.class, WithField.class,
				WithoutAnyFactoryMethod.class);
		assertCanAdaptInstance(FactoryInterface.class, WithField.class,
				WithoutAnyFactoryMethod.class);
		final Object instance = Duck.type(WithField.class,
				FactoryInterface.class).getInstance();
		Assert.assertNotNull(instance);
		Assert.assertEquals(WithField.class, instance.getClass());

	}

}
