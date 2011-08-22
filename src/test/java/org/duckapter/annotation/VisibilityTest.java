package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;
import static org.junit.Assert.assertTrue;

import org.duckapter.adapted.AdaptedFactory;
import org.duckapter.checker.Visibility;
import org.junit.Test;

public class VisibilityTest {

	@Package
	public static interface PackageInterface {
	}

	@Public
	public static interface PublicInterface {
	}

	@Protected
	public static interface ProtectedInterface {
	}

	@Private
	public static interface PrivateInterface {
	}

	public static class PublicClass {
	};

	static class PackageClass {
	};

	protected static class ProtectedClass {
	};

	private static class PrivateClass {
	}

	@Test
	public void testPackageInterface() throws Exception {
		assertCanAdaptClass(PackageInterface.class, PackageClass.class,
				PublicClass.class);
		assertCanAdaptInstance(PackageInterface.class, PackageClass.class,
				PublicClass.class);
	}

	@Test
	public void testPublicInterface() throws Exception {
		assertCanAdaptClass(PublicInterface.class, PublicClass.class,
				PackageClass.class);
		assertCanAdaptInstance(PublicInterface.class, PublicClass.class,
				PackageClass.class);
	}

	@Test
	public void testProtectedInterface() throws Exception {
		assertCanAdaptClass(ProtectedInterface.class, ProtectedClass.class,
				PackageClass.class);
		assertCanAdaptInstance(ProtectedInterface.class, ProtectedClass.class,
				PublicClass.class);
	}

	@Test
	public void testPrivateInterface() throws Exception {
		assertCanAdaptClass(PrivateInterface.class, PrivateClass.class,
				PackageClass.class);
		assertCanAdaptInstance(PrivateInterface.class, PrivateClass.class,
				PublicClass.class);
	}

	public static class WithPackageMethodClass {
		void doIt() {
		};
	}

	public static class WithPublicMethodClass {
		public void doIt() {
		}
	}

	public static class WithProtectedMethodClass {
		protected void doIt() {
		}
	}

	public static class WithPrivateMethodClass {
		@SuppressWarnings("unused")
		private void doIt() {
		}
	}

	public static interface WithPublicMethodInterfaceAtLeast {
		@Public(Visibility.AT_LEAST)
		void doIt();
	}

	public static interface WithPublicMethodInterfaceAtMost {
		@Public(Visibility.AT_MOST)
		void doIt();
	}

	public static interface WithPublicMethodInterface {
		@Public
		void doIt();
	}

	@Test
	public void testPublicMethod() throws Exception {
		assertCanAdaptInstance(WithPublicMethodInterface.class,
				WithPublicMethodClass.class, WithPackageMethodClass.class);
		assertCanAdaptInstance(WithPublicMethodInterface.class,
				WithPublicMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPublicMethodInterface.class,
				WithPublicMethodClass.class, WithPrivateMethodClass.class);

		assertTrue(AdaptedFactory.adapt(WithProtectedMethodClass.class, WithPublicMethodInterfaceAtMost.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithPackageMethodClass.class, WithPublicMethodInterfaceAtMost.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithPublicMethodClass.class, WithPublicMethodInterfaceAtMost.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithPrivateMethodClass.class, WithPublicMethodInterfaceAtMost.class)
		.canAdaptInstance());

		assertCanAdaptInstance(WithPublicMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPublicMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithPackageMethodClass.class);
		assertCanAdaptInstance(WithPublicMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithPrivateMethodClass.class);
	}

	public static interface WithPackageMethodInterfaceAtLeast {
		@Package(Visibility.AT_LEAST)
		void doIt();
	}

	public static interface WithPackageMethodInterfaceAtMost {
		@Package(Visibility.AT_MOST)
		void doIt();
	}

	public static interface WithPackageMethodInterface {
		@Package
		void doIt();
	}

	@Test
	public void testPackageMethod() throws Exception {
		assertCanAdaptInstance(WithPackageMethodInterface.class,
				WithPackageMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterface.class,
				WithPackageMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterface.class,
				WithPackageMethodClass.class, WithPrivateMethodClass.class);

		assertCanAdaptInstance(WithPackageMethodInterfaceAtMost.class,
				WithPackageMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterfaceAtMost.class,
				WithProtectedMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterfaceAtMost.class,
				WithPrivateMethodClass.class, WithPublicMethodClass.class);

		assertCanAdaptInstance(WithPackageMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPackageMethodInterfaceAtLeast.class,
				WithPackageMethodClass.class, WithPrivateMethodClass.class);
	}

	public static interface WithProtectedMethodInterfaceAtLeast {
		@Protected(Visibility.AT_LEAST)
		void doIt();
	}

	public static interface WithProtectedMethodInterfaceAtMost {
		@Protected(Visibility.AT_MOST)
		void doIt();
	}

	public static interface WithProtectedMethodInterface {
		@Protected
		void doIt();
	}

	@Test
	public void testProtectedMethod() throws Exception {
		assertCanAdaptInstance(WithProtectedMethodInterface.class,
				WithProtectedMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterface.class,
				WithProtectedMethodClass.class, WithPackageMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterface.class,
				WithProtectedMethodClass.class, WithPrivateMethodClass.class);

		assertCanAdaptInstance(WithProtectedMethodInterfaceAtMost.class,
				WithProtectedMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterfaceAtMost.class,
				WithProtectedMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterfaceAtMost.class,
				WithPrivateMethodClass.class, WithPackageMethodClass.class);

		assertCanAdaptInstance(WithProtectedMethodInterfaceAtLeast.class,
				WithPublicMethodClass.class, WithPrivateMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterfaceAtLeast.class,
				WithProtectedMethodClass.class, WithPrivateMethodClass.class);
		assertCanAdaptInstance(WithProtectedMethodInterfaceAtLeast.class,
				WithPackageMethodClass.class, WithPrivateMethodClass.class);
	}

	public static interface WithPrivateMethodInterfaceAtLeast {
		@Private(Visibility.AT_LEAST)
		void doIt();
	}

	public static interface WithPrivateMethodInterfaceAtMost {
		@Private(Visibility.AT_MOST)
		void doIt();
	}

	public static interface WithPrivateMethodInterface {
		@Private
		void doIt();
	}

	@Test
	public void testPrivateMethod() throws Exception {
		assertCanAdaptInstance(WithPrivateMethodInterface.class,
				WithPrivateMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithPrivateMethodInterface.class,
				WithPrivateMethodClass.class, WithPackageMethodClass.class);
		assertCanAdaptInstance(WithPrivateMethodInterface.class,
				WithPrivateMethodClass.class, WithProtectedMethodClass.class);

		assertCanAdaptInstance(WithPrivateMethodInterfaceAtMost.class,
				WithPrivateMethodClass.class, WithPublicMethodClass.class);
		assertCanAdaptInstance(WithPrivateMethodInterfaceAtMost.class,
				WithPrivateMethodClass.class, WithProtectedMethodClass.class);
		assertCanAdaptInstance(WithPrivateMethodInterfaceAtMost.class,
				WithPrivateMethodClass.class, WithPackageMethodClass.class);

		assertTrue(AdaptedFactory.adapt(WithPrivateMethodClass.class, WithPrivateMethodInterfaceAtLeast.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithProtectedMethodClass.class, WithPrivateMethodInterfaceAtLeast.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithPackageMethodClass.class, WithPrivateMethodInterfaceAtLeast.class)
		.canAdaptInstance());
		assertTrue(AdaptedFactory.adapt(WithPublicMethodClass.class, WithPrivateMethodInterfaceAtLeast.class)
		.canAdaptInstance());
	}
}
