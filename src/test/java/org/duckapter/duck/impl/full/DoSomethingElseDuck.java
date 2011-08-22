package org.duckapter.duck.impl.full;

public class DoSomethingElseDuck {

	public static int count = 0;

	private final String name;

	public DoSomethingElseDuck() {
		this("Donald");
	}

	public DoSomethingElseDuck(String name) {
		this.name = name;
		count++;
	}

	public String getName() {
		return name;
	}

	public void dive() {
		
	}

	public void doSomethingElse() {
		
	}

	public static boolean canFly() {
		return true;
	}

	@Override
	public String toString() {
		return "Duck called " + getName();
	}
}
