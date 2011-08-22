package org.duckapter.annotation;

import org.duckapter.Duck;
import org.duckapter.DuckTestHelper;
import org.junit.Assert;
import org.junit.Test;

public class OptionalTest {

	public static interface OptionalInterface {
		void doIt();

		@Optional
		String doItOptional();
		
		@Optional
		int doItOptionalInt();
		
		@Optional
		double doItOptionalDouble();
	}

	public static class OptionalClass {
		public void doIt() {
		}
	}

	public static class FailClass {
	}

	@Test
	public void testOptional() {
		DuckTestHelper.assertCanAdaptInstance(OptionalInterface.class,
				OptionalClass.class, FailClass.class);
	}

	@Test
	public void testMethodCalls() {
		OptionalInterface opt = Duck.type(new OptionalClass(), OptionalInterface.class);
		Assert.assertNull(opt.doItOptional());
		Assert.assertEquals(0, opt.doItOptionalInt());
		Assert.assertEquals(0, opt.doItOptionalDouble(), 0.1);
	}

}
