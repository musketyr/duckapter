package org.duckapter.duck.impl.full;

public class FactoryDuck {

	public static int count = 0;

	private final String name;

	public FactoryDuck newDuck() {
		return new FactoryDuck("Donald");
	}

	public FactoryDuck(String name) {
		this.name = name;
		count++;
	}

	public String getName() {
		return name;
	}

	public void dive() {
		
	}

	public static boolean canFly() {
		return true;
	}

	@Override
	public String toString() {
		return "Duck called " + getName();
	}
}
