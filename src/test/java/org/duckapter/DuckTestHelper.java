package org.duckapter;

import static org.junit.Assert.assertFalse;

import org.duckapter.adapted.AdaptedFactory;
import org.junit.Assert;

public final class DuckTestHelper {
	private DuckTestHelper() {
	}

	/**
	 * Asserts whether the instance of the toPass class can be ducked into the
	 * tested interface but the toFail can't. Both toPass and toFail classes
	 * should be instantiated with default constructor if possible.
	 * 
	 * @param testedInterface
	 *            the tested interface
	 * @param toPass
	 *            class which can be duck typed to the test interface
	 * @param toFail
	 *            class which can't be duck typed to the test interface
	 */
	public static final void assertCanAdaptInstance(Class<?> testedInterface,
			Class<?> toPass, Class<?> toFail) {
		AdaptedFactory.clearCache();
		Assert.assertNotNull(testedInterface);
		Assert.assertTrue(testedInterface.isInterface());
		Assert.assertNotNull(toPass);
		Assert.assertNotNull(toFail);

		try {
			try {
				Duck.type(toFail.newInstance(), testedInterface);
				Assert.fail();
			} catch (AdaptationException e) {
				// ok
			}
			assertFalse(Duck.test(toFail.newInstance(),
					testedInterface));
		} catch (InstantiationException e) {
			// ok
		} catch (IllegalAccessException e) {
			// ok
		}

		try {
			try {
				Duck.type(toPass.newInstance(), testedInterface);
				// ok
			} catch (AdaptationException e) {
				Assert.fail(e.getMessage());
			}
		} catch (InstantiationException e) {
			// ok
		} catch (IllegalAccessException e) {
			// ok
		}
	}

	/**
	 * Asserts whether the toPass class can be ducked into the tested interface
	 * but the toFail can't. Both toPass and toFail classes should be
	 * instantiated with default constructor if possible.
	 * 
	 * @param testedInterface
	 *            the tested interface
	 * @param toPass
	 *            class which can be duck typed to the test interface
	 * @param toFail
	 *            class which can't be duck typed to the test interface
	 */
	public static final void assertCanAdaptClass(Class<?> testedInterface,
			Class<?> toPass, Class<?> toFail) {
		assertFalse(Duck.test(toFail, testedInterface));

		Assert.assertNotNull(testedInterface);
		Assert.assertTrue(testedInterface.isInterface());
		Assert.assertNotNull(toPass);
		Assert.assertNotNull(toFail);

		try {
			Duck.type(toFail, testedInterface);
			Assert.fail();
		} catch (AdaptationException e) {
			// ok
		}

		try {
			Duck.type(toPass, testedInterface);

		} catch (AdaptationException e) {
			Assert.fail(e.getMessage());
		}

	}
}
