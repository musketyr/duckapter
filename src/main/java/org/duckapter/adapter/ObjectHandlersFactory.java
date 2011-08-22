package org.duckapter.adapter;


import org.duckapter.adapted.AdaptedFactory;

final class ObjectHandlersFactory {

	private static final PrimitivesObjectHandler PRIMITIVES_OBJECT_HANDLER = new PrimitivesObjectHandler();
	private static final DirectObjectHandler DIRECT_OBJECT_HANDLER = new DirectObjectHandler();

	private ObjectHandlersFactory() {
	}

	static final ObjectHandler getHandler(Class<?> objectsClass,
			Class<?> desiredType) {
		if (desiredType.isAssignableFrom(objectsClass)) {
			return DIRECT_OBJECT_HANDLER;
		}
		if (desiredType.isPrimitive()) {
			return PRIMITIVES_OBJECT_HANDLER;
		}
		if (desiredType.isInterface()
				&& AdaptedFactory.adapt(objectsClass, desiredType)
						.canAdaptInstance()) {
			return new DuckObjectHandler(desiredType);
		}
		return DIRECT_OBJECT_HANDLER;
		// TODO solve the problem with the AllAdapter first, then return the
		// throw clause
		//throw new IllegalArgumentException("Unable to create object hander!");
	}

}
