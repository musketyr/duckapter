package org.duckapter.duck.itest;

import org.duckapter.annotation.Constructor;
import org.duckapter.annotation.Factory;
import org.duckapter.annotation.Property;
import org.duckapter.annotation.Static;

public interface IDuckClass {
	@Factory
	IDuck newDuck();

	@Constructor
	IDuck newDuck(String name);

	@Static
	boolean canFly();

	@Static
	@Property
	int getCount();
}
