package org.duckapter.duck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.duckapter.Duck;
import org.duckapter.duck.itest.IDuck;
import org.duckapter.duck.itest.IDuckClass;
import org.junit.Test;

public abstract class AbstractSimpleDuckTest {

	protected final Class<?> fixure;

	public AbstractSimpleDuckTest(Class<?> fixure) {
		super();
		this.fixure = fixure;
	}



	@Test
	public void canDuckTrue() throws Exception {
		assertTrue(Duck.test(Duck.type(fixure,
				IDuckClass.class).newDuck(), IDuck.class));
	}
	
	@Test
	public void canDuckFalse() throws Exception {
		assertFalse(Duck.test((Object)fixure, IDuckClass.class));
	}

}