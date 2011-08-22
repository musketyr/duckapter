package org.duckapter.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.duckapter.checker.StereotypeType.AND;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.duckapter.CheckerAnnotation;
import org.duckapter.DuckTestHelper;
import org.duckapter.checker.MethodsOnlyChecker;
import org.duckapter.checker.PublicOnlyChecker;
import org.duckapter.checker.StereotypeCheckerChecker;
import org.duckapter.checker.Visibility;
import org.junit.Test;

public class StereotypeTest {


	public static interface MyFactoryIFace {
		@Factory
		Object instance();
	}

	public static class MyFactoryConstructor {
		public MyFactoryConstructor() {
		}
	}

	public static class MyFactoryMethod {
		public static Object instance() {
			return null;
		}
	}

	public static class MyFactoryField {
		public static Object INSTANCE = null;
	}

	public static class MyFactoryFail {
		private MyFactoryFail() {
		}
	}

	@Test
	public void testMyFactoryConstructor() {
		DuckTestHelper.assertCanAdaptInstance(MyFactoryIFace.class,
				MyFactoryConstructor.class, MyFactoryFail.class);
	}

	@Test
	public void testMyFactoryField() {
		DuckTestHelper.assertCanAdaptInstance(MyFactoryIFace.class,
				MyFactoryField.class, MyFactoryFail.class);
	}

	@Test
	public void testMyFactoryMethod() {
		DuckTestHelper.assertCanAdaptInstance(MyFactoryIFace.class,
				MyFactoryMethod.class, MyFactoryFail.class);
	}

	@Documented
	@CheckerAnnotation(StereotypeCheckerChecker.class)
	@StereotypeChecker(AND)
	@Retention(RUNTIME)
	@Target( { METHOD, ANNOTATION_TYPE })
	@Package(Visibility.AT_MOST)
	@Static
	@Final
	@Field
	
	@CanCheck(ElementType.FIELD)
	@SuppressChecker({MethodsOnlyChecker.class, PublicOnlyChecker.class})
	public static @interface AtMostPackageConst {
	}

	public static interface AMCPInterface {
		@AtMostPackageConst
		String constant();
	}

	public static class AMCPClassPass {
		static final String CONSTANT = "BLA";
	}

	public static class AMCPClassFail {
		public static final String CONSTANT = "BLA";
	}

	@Test
	public void testAMCP() {
		DuckTestHelper.assertCanAdaptInstance(AMCPInterface.class,
				AMCPClassPass.class, AMCPClassFail.class);
	}

	@SuppressWarnings("unused")
	@StereotypeChecker
	private static @interface TEST {
	}

}
