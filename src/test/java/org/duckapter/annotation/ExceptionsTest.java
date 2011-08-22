package org.duckapter.annotation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class ExceptionsTest {

	public static interface NoExceptionInterface {
		void test();
	}

	public static interface ExceptionInterface {
		void test() throws IOException;
	}

	public static class NoExceptionClass {
		public void test() {
		}
	}

	public static class SubExceptionClass {
		public void test() throws FileNotFoundException {
		}
	}

	public static class SameExceptionClass {
		public void test() throws IOException {
		}
	}

	public static class OtherExceptionClass {
		public void test() throws SQLException {
		}
	}

	@Test
	public void testNoEx() throws IOException {
		DuckTestHelper.assertCanAdaptInstance(NoExceptionInterface.class,
				NoExceptionClass.class, SameExceptionClass.class);
	}
	
	@Test
	public void testSubEx() throws IOException {
		DuckTestHelper.assertCanAdaptInstance(ExceptionInterface.class,
				SubExceptionClass.class, OtherExceptionClass.class);
	}
	
	@Test
	public void testSameEx() throws IOException {
		DuckTestHelper.assertCanAdaptInstance(ExceptionInterface.class,
				SameExceptionClass.class, OtherExceptionClass.class);
	}
	
	@Test
	public void testNoEx2() throws IOException {
		DuckTestHelper.assertCanAdaptInstance(ExceptionInterface.class,
				NoExceptionClass.class, OtherExceptionClass.class);
	}

}
