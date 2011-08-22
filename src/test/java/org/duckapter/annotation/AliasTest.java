package org.duckapter.annotation;

import static org.duckapter.Duck.type;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class AliasTest {

	public static interface WithAliasInterface {
		@Alias("doItNow")
		String doIt();
	}

	public static class WithOriginalMethod {
		public String doIt() {
			return "doIt";
		}
	}

	public static class WithAliasedMethod {
		public String doItNow() {
			return "doItNow";
		}
	}

	public static class WithBothMethods {
		public String doIt() {
			return "doIt";
		}

		public String doItNow() {
			return "doItNow";
		}
	}

	public static class WithoutAnyMethod {
		public String justKidding() {
			return "justKidding";
		}
	}

	@Test
	public void testAlias() {
		assertCanAdaptInstance(WithAliasInterface.class,
				WithOriginalMethod.class, WithoutAnyMethod.class);
		assertCanAdaptInstance(WithAliasInterface.class,
				WithAliasedMethod.class, WithoutAnyMethod.class);
		assertCanAdaptInstance(WithAliasInterface.class, WithBothMethods.class,
				WithoutAnyMethod.class);
		Assert.assertEquals("doIt", type(new WithOriginalMethod(),
				WithAliasInterface.class).doIt());
		Assert.assertEquals("doItNow", type(new WithAliasedMethod(),
				WithAliasInterface.class).doIt());

	}

	@Test
	@Ignore
	public void testRightOrder() {
		Assert.assertEquals("doIt", type(new WithBothMethods(),
				WithAliasInterface.class).doIt());
	}

}
