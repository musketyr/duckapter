package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class AbstractTest {

	@Abstract
	public static interface AbstractInterface {
	};

	public static class NonAbstractClass {
	};

	public static abstract class AbstractClass {
	};

	@Test
	public void testAbstractClass() {
		assertCanAdaptClass(AbstractInterface.class, AbstractClass.class,
				NonAbstractClass.class);
	}

	@Test
	public void testAbstractClassInstance() {
		assertCanAdaptInstance(AbstractInterface.class, AbstractClass.class,
				NonAbstractClass.class);
	}

	public static interface InterfaceWithAbstractMethod {
		@Abstract
		void doIt();
	}

	public static class WithConcereteMethod {
		public void doIt() {
		}
	}

	public static abstract class WithAbstractMethod {
		public abstract void doIt();
	}

	@Test
	public void testAbstractMethod() {
		assertCanAdaptInstance(InterfaceWithAbstractMethod.class,
				WithAbstractMethod.class, WithConcereteMethod.class);
	}

	public static interface InterfaceWithAbstractConstructor {
		@Abstract
		@Constructor
		Object novaInstance();
	}

}
