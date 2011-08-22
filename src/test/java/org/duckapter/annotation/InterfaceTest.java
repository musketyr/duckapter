package org.duckapter.annotation;

import org.duckapter.Duck;
import org.junit.Assert;
import org.junit.Test;

public class InterfaceTest {

	public static interface TheInterface {
	}

	public static interface OtherInterface {
	}

	public static class TheClass {
	}

	@Test
	public void testAdaptClass() {
		Assert.assertFalse(Duck.test(TheInterface.class,
				TheClass.class));
	}

	@Test
	public void testAdaptInterface() {
		Assert.assertFalse(Duck.test(TheInterface.class,
				TheClass.class));
	}
	
}
