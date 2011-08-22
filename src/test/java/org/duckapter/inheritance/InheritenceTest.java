package org.duckapter.inheritance;

import org.duckapter.Duck;
import org.duckapter.annotation.Factory;
import org.duckapter.annotation.Static;
import org.junit.Test;

public class InheritenceTest {

	public static class Inheritance {

		public static void testStatic() {
		};

		public void testInstance() {
		};

	}
	
	public static class Super {
		public void testInstance(){}
	}
	
	public static class Inheritence2 extends Super {
		public static void testStatic(){}
	}
	
	public static interface InheritanceInstance extends InheritanceClass {
		void testInstance();
	}

	public static interface InheritanceClass {

		@Factory
		InheritanceInstance instance();

		@Static
		void testStatic();

	}
	
	@Test public void testInheritance0(){
		Duck.type(new Inheritance(), InheritanceClass.class);
	}
	
	@Test public void testInheritance1(){
		Duck.type(new Inheritance(), InheritanceInstance.class);
	}
	
	@Test
	public void testInheritance() throws Exception {
		InheritanceClass iclass = Duck.type(Inheritance.class,
				InheritanceClass.class);
		iclass.testStatic();
		InheritanceInstance iinstance = iclass.instance();
		iinstance.testStatic();
		iinstance.testInstance();

	}
	
	@Test public void testInheritance2(){
		Duck.type(new Inheritence2(), InheritanceClass.class);
	}
	
	@Test public void testInheritance3(){
		Duck.type(new Inheritence2(), InheritanceInstance.class);
	}
	
	@Test
	public void testSuper() throws Exception {
		InheritanceClass iclass = Duck.type(Inheritence2.class,
				InheritanceClass.class);
		iclass.testStatic();
		InheritanceInstance iinstance = iclass.instance();
		iinstance.testStatic();
		iinstance.testInstance();

	}

}
