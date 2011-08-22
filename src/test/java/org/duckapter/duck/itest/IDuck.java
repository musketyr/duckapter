package org.duckapter.duck.itest;

import org.duckapter.annotation.Property;

public interface IDuck {

	@Property
	String getName();

	void dive();

}
