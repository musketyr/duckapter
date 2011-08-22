package org.duckapter.duck.impl.full;

public class StaticGetterDuck {

	private static int count = 0;

	public static int getCount() {
		return count;
	}

	private final String name;

	public StaticGetterDuck() {
		this("Donald");
	}

	public StaticGetterDuck(String name) {
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
