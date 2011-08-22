package org.duckapter.annotation;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class ConcreteMethodsTest {

	public static interface ConcreteMethod {
		void doIt();
	}

	public static class WithConcrete {
		public void doIt() {
		}
	}

	public static abstract class SuperWithoutConcrete {
		public abstract void doIt();
	}

	@Test
	public void testConcreteMethod() {
		DuckTestHelper.assertCanAdaptInstance(ConcreteMethod.class,
				WithConcrete.class, SuperWithoutConcrete.class);
	}

}
