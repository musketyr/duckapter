package org.duckapter;

/**
 * Interface {@link Adapted} wraps original object and allows it to be adapted
 * to the desired interface.
 * 
 * @author Vladimir Orany
 * 
 * @param <O>
 *            the type of original object
 * @param <D>
 *            the duck interface
 */
public interface Adapted<O, D> {

	/**
	 * @return the original instance of adapted object
	 */
	O getOriginalInstance();

	/**
	 * @return the adapted class describing the adaptation
	 */
	AdaptedClass<O, D> getAdaptedClass();



}
