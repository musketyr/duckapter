package org.duckapter.duck.itest;

import org.duckapter.duck.AbstractFullDuckTest;
import org.duckapter.duck.impl.full.DoSomethingElseDuck;

public class FactoryDuckTest extends AbstractFullDuckTest {

	public FactoryDuckTest() {
		super(DoSomethingElseDuck.class);
	}

}
