package org.duckapter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.duckapter.annotation.Alias;
import org.duckapter.annotation.Property;
import org.duckapter.annotation.Static;
import org.junit.Test;

public class AliasTest {

	public static class AliasClass {

		public static String getSomethingElse() {
			return null;
		}

		public static String getLastOption() {
			return null;
		}

		public static String property = null;
	}

	public interface IAlias {

		@Static
		@Alias("getSomethingElse")
		String getSomething();

		@Static
		@Alias( { "getMoreOptions", "getLotMoreOptions", "getLastOption" })
		String getString();

		@Static
		@Property
		@Alias("property")
		String getProperty();

	}

	@Test
	public void testAlias() throws Exception {
		assertTrue(Duck.test(AliasClass.class, IAlias.class));
		try {
			Duck.type(AliasClass.class, IAlias.class);
		} catch (Exception e) {
			fail();
		}

	}

}
