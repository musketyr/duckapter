package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptClass;
import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Ignore;
import org.junit.Test;

public class MatchingTest {

	public static interface MatchingInterface {
		@Matching("^test.*")
		void testuj();
	}

	public static class MatchingClass {
		public void test() {
		}
	}

	public static class NonMatchingClass {
		public void pest() {
		}
	}

	@Test
	public void testMatching() {
		assertCanAdaptInstance(MatchingInterface.class, MatchingClass.class,
				NonMatchingClass.class);
	}

	public static interface M2 {
		@Matching(value = "^java${name}", caseInsensitive = true)
		void rules();
	}

	public static class M2F {
		public void javaDoesntRule() {
		}
	}

	public static class M2P {
		public void javaRules() {
		}
	}

	@Test
	@Ignore
	public void testRules() {
		assertCanAdaptInstance(M2.class, M2P.class, M2F.class);
	}

	public static interface M3 {
		@Matching(value = "get${className}", caseInsensitive = true)
		Object getInstance();
	}

	public static class M3F {
		public Object getInstance() {
			return null;
		}
	}

	public static class M3P {
		public Object getM3P() {
			return null;
		}
	}

	@Test
	@Ignore
	public void testInstance() {
		assertCanAdaptInstance(M3.class, M3P.class, M3F.class);
	}

	@Matching(".*?Testovaci$")
	public static interface MT {
	}

	public static class TestovaciTrida {
	}

	public static class TridaTestovaci {
	}

	@Test
	public void testTrida() {
		assertCanAdaptClass(MT.class, TridaTestovaci.class,
				TestovaciTrida.class);
		assertCanAdaptInstance(MT.class, TridaTestovaci.class,
				TestovaciTrida.class);
	}

}
