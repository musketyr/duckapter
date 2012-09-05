package org.duckapter;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * The {@link AdaptedClass} stores information about the class of original
 * instance and the duck interface. It provides methods {@link #canAdaptClass()}
 * and {@link #canAdaptInstance()} which returns whether or not will be
 * adaptation for instance or class successful. If adaptation is not successful
 * methods {@link #getUnimplementedForClass()} and
 * {@link #getUnimplementedForInstance()} returns collection of methods which
 * cannot find their counterparts. If adaptation fails but the
 * {@link #getUnimplementedForClass()} or {@link #getUnimplementedForInstance()}
 * returns empty collection the class itself doesn't correspond to the duck
 * interface.
 * 
 * @author Vladimir Orany
 * 
 * @param <O>
 *            the original class
 * @param <D>
 *            the desired interface
 */
public interface AdaptedClass<O, D> {

	/**
	 * Invokes specified duck method on the supplied instance using appropriate
	 * stored invocation adapter. The supplied object can be <code>null</code>
	 * when invoking class level methods.
	 * 
	 * @param object
	 *            object to be methods invoked on
	 * @param duckMethod
	 *            invoked method of the duck interface
	 * @param args
	 *            arguments supplied into the duck method
	 * @return result of the invocation
	 * @throws Throwable
	 *             on exception during invocation
	 */
	Object invoke(Object object, Method duckMethod, Object[] args) throws Throwable;

	/**
	 * @return the original class
	 */
	Class<O> getOriginalClass();

	/**
	 * @return the desired duck interface
	 */
	Class<D> getDuckInterface();

	/**
	 * Returns <code>true</code> if all methods from duck interface can be
	 * invoked on the instance of original class.
	 * 
	 * @return whether the instances of original class can be adapted to the
	 *         desired interface
	 */
	boolean canAdaptInstance();

	/**
	 * Returns <code>true</code> if all methods from duck interface can be
	 * invoked on the original class.
	 * 
	 * @return whether the original class can be adapted to the desired duck
	 *         interface
	 */
	boolean canAdaptClass();

	/**
	 * @return {@link Collection} of unimplemented methods at instance level for
	 *         specified pair original class - duck interface
	 */
	Collection<Method> getUnimplementedForInstance();

	/**
	 * @return {@link Collection} of unimplemented methods at class level for
	 *         specified pair original class - duck interface
	 */
	Collection<Method> getUnimplementedForClass();
	
	/**
	 * Tries to adapt the original instance to the desired interface. Interface
	 * can contain instance and class level (static) methods.
	 * 
	 * @param o the object to be adapted
	 * @return adapted instance if possible
	 * @throws AdaptationException
	 * @see Duck#test(Object, Class)
	 * @see Duck#type(Object, Class)
	 * @see AdaptedClass#canAdaptInstance()
	 */
	D adaptInstance(O o);

	/**
	 * Tries to adapt the original class to the desired interface. Interface can
	 * contain class level (static) methods but not instance level methods. The
	 * {@link #getOriginalInstance() original instance} is usually
	 * <code>null</code> when this method succeed.
	 * 
	 * @return adapted instance if possible
	 * @throws AdaptationException
	 * @see Duck#test(Class, Class)
	 * @see Duck#type(Class, Class)
	 * @see AdaptedClass#canAdaptClass()
	 */
	D adaptClass();

}
