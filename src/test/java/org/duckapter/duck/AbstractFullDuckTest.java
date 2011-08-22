package org.duckapter.duck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.duckapter.Duck;
import org.duckapter.duck.itest.IDuck;
import org.duckapter.duck.itest.IDuckClass;
import org.junit.Test;

public abstract class AbstractFullDuckTest extends AbstractSimpleDuckTest {

	protected AbstractFullDuckTest(Class<?> fixure) {
		super(fixure);
	}

	@Test
	public void canDuckClass() throws Exception {
		assertTrue(Duck.test(fixure, IDuckClass.class));
		assertFalse(Duck.test(fixure, IDuck.class));
	}

	@Test
	public void newInstance() throws Exception {
		IDuckClass duck = Duck.type(fixure, IDuckClass.class);
		assertEquals("Donald", duck.newDuck().getName());
	}

	@Test
	public void newInstanceString() throws Exception {
		IDuck duck = Duck.type(fixure, IDuckClass.class).newDuck(
				"Other");
		assertEquals("Other", duck.getName());
	}

	@Test
	public void testMethodsWorks() throws Exception {
		IDuck duck = Duck.type(fixure, IDuckClass.class).newDuck(
				"Other");
		assertEquals("Other", duck.getName());
		duck.dive();
		assertNotNull(duck.toString());
	}

	@Test
	public void staticMethodWorks() throws Exception {
		IDuckClass duckClass = Duck.type(fixure, IDuckClass.class);
		duckClass.getCount();
	}

}
