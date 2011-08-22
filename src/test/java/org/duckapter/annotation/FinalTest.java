package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class FinalTest {
	@Final
	public interface FinalInterface {
	}

	public final class FinalClass {
	};

	public class NonFinalClass {
	};

	@Test
	public void testFinalInterface() {
		assertCanAdaptClass(FinalInterface.class, FinalClass.class,
				NonFinalClass.class);
		assertCanAdaptInstance(FinalInterface.class, FinalClass.class,
				NonFinalClass.class);
	}

	public interface WithFinalFieldInterface {
		@Final
		@Field
		String field();
	};

	public class WithFinalField {
		public final String field = "Bla";
	}

	public class WithoutFinalField {
		public String field = "Bla";
	}

	@Test
	public void testFinalField() {
		assertCanAdaptInstance(WithFinalFieldInterface.class,
				WithFinalField.class, WithoutFinalField.class);
	}

	public interface WithFinalMethodInterface {
		@Final
		void doIt();
	}

	public class WithFinalMethod {
		public final void doIt() {
		}
	}

	public class WithoutFinalMethod {
		public void doIt() {
		}
	}

	@Test
	public void testFinalMethod() {
		assertCanAdaptInstance(WithFinalMethodInterface.class,
				WithFinalMethod.class, WithoutFinalMethod.class);
	}

//	public interface WithFinalParameterInterface {
//		void doIt(@Final String str);
//	}

	public class WithFinalParameter {
		public void doIt(final String str) {
		}
	}

	public class WithoutFinalParameter {
		public void doIt(String str) {
		}
	}
//
//	@Test
//	@Ignore
//	public void testFinalParameter() {
//		assertCanAdaptInstance(WithFinalParameterInterface.class,
//				WithFinalParameter.class, WithoutFinalParameter.class);
//	}

}
