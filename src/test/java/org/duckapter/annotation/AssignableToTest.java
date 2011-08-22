package org.duckapter.annotation;

import java.io.Serializable;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class AssignableToTest {

	@AssignableTo(Serializable.class)
	public static interface SerializableInterface {
	}

	@SuppressWarnings("serial")
	public static class SerializableClass implements Serializable {
	}

	public static class NonSerializableClass {
	}
	
	@AssignableTo(NonSerializableClass.class)
	public static interface AssignableToClassInterface {
	}

	@AssignableTo({SerializableClass.class, Serializable.class})
	public static interface AssignableToClassAndSerializable{}
	
	@Test
	public void test() {
		DuckTestHelper.assertCanAdaptClass(SerializableInterface.class,
				SerializableClass.class, NonSerializableClass.class);
	}
	
	@Test
	public void test2() {
		DuckTestHelper.assertCanAdaptClass(AssignableToClassInterface.class,
				NonSerializableClass.class, SerializableClass.class);
	}
	
	@Test
	public void test3() {
		DuckTestHelper.assertCanAdaptClass(AssignableToClassAndSerializable.class,
				SerializableClass.class, NonSerializableClass.class);
	}

}
