package org.duckapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.duckapter.adapted.AdaptedFactory;
import org.duckapter.annotation.Static;
import org.duckapter.checker.Checkers;

/**
 * The {@link Duck} class is facade for the Duckapter framework. It provides the
 * core methods {@link #type(Class, Class)}, {@link #type(Object, Class)},
 * {@link #test(Class, Class)} and {@link #type(Object, Class)} which can users
 * use for duck testing and duck typing. 
 * 
 * @author Vladimir Orany
 */
public final class Duck {

	/**
	 * Private constructor prevents creation of new instances of this class.
	 */
	private Duck() {
		// prevents instance creation and subtyping
	}

	/**
	 * Adapts original object to the duck interface. The method
	 * {@link #test(Object, Class)} must return <code>true</code> for the same
	 * pair of parameters otherwise {@link AdaptationException} will be thrown.
	 * Adapted object can access instance and class (static) level elements of
	 * original instance.
	 * 
	 * @see #test(Object, Class)
	 * 
	 * @param <O>
	 *            the type of original object
	 * @param <D>
	 *            the duck interface
	 * @param original
	 *            the object to be adapted
	 * @param duck
	 *            the desired interface of adapted object
	 * @return original object adapted to the duck interface if possible
	 * @throws AdaptationException
	 */
	public static <O, D> D type(final O original, final Class<D> duck) {
		if (original == null) {
			return null;
		}
		Class<O> originalClass = getOriginalClass(original);
		return AdaptedFactory.adapt(originalClass, duck)
				.adaptInstance(original);
	}

	@SuppressWarnings("unchecked")
	private static <O> Class<O> getOriginalClass(final O original) {
		if (original == null) {
			return null;
		}
		Class<O> originalClass = (Class<O>) original.getClass();
		if(Proxy.isProxyClass(originalClass)){
			InvocationHandler hander = Proxy.getInvocationHandler(original);
			if (hander instanceof Adapted) {
				Adapted<O,?> adapted = (Adapted<O,?>) hander;
				originalClass = adapted.getAdaptedClass().getOriginalClass();
			} else if (hander instanceof AdaptedClass) {
				AdaptedClass<O,?> adaptedClass = (AdaptedClass<O,?>) hander;
				originalClass = adaptedClass.getOriginalClass();
				
			}
		}
		return originalClass;
	}

	/**
	 * Adapts original class object to the duck interface. The method
	 * {@link #test(Class, Class)} must return <code>true</code> for the same
	 * pair of parameters otherwise {@link AdaptationException} will be thrown.
	 * Adapted object can access only class level (static) elements.
	 * Constructors are considered static elements too.
	 * 
	 * @see #test(Class, Class)
	 * 
	 * @param <O>
	 *            the original class
	 * @param <D>
	 *            the duck interface
	 * @param original
	 *            the class to be adapted
	 * @param duck
	 *            the desired interface of adapted object
	 * @return original class object adapted to the duck interface
	 * @throws AdaptationException
	 */
	public static <O, D> D type(final Class<O> original, final Class<D> duck) {
		return AdaptedFactory.adapt(original, duck).adaptClass();
	}

	/**
	 * Tests whether the original class object can be adapted to the specified
	 * duck interface. The following conditions must be met:
	 * <ul>
	 * <li>parameter <code>duck</code> must be an interface
	 * <li>all methods declared by the <code>duck</code> interface must be
	 * accessible on the class level. This practically mean that must be
	 * annotated by {@link Static}, {@link Constructor} or other "static"
	 * annotation like {@link Factory}.
	 * <li>all active {@link Checkers#getDefaultCheckers() default checkers}
	 * must comply for class and each duck interface method where appropriate
	 * <li>all checkers from duck interface must comply for the original class
	 * <li>all methods from the duck interface must have their counterpart
	 * unless the {@link Checker checker} from annotation declares not to need
	 * them
	 * </ul>
	 * 
	 * @param <O>
	 *            the original class
	 * @param <D>
	 *            the duck interface
	 * @param original
	 *            the class to be adapted
	 * @param duck
	 *            the desired interface of adapted object
	 * @return whether the original class can be adapted to the desired
	 *         interface
	 */
	public static <O, D> boolean test(final Class<O> original,
			final Class<D> duck) {
		return AdaptedFactory.adapt(original, duck).canAdaptClass();
	}

	/**
	 * Tests whether the original class object can be adapted to the specified
	 * duck interface. The following conditions must be met:
	 * <ul>
	 * <li>parameter <code>duck</code> must be an interface
	 * <li>all active {@link Checkers#getDefaultCheckers() default checkers}
	 * must comply for class and each duck interface method where appropriate
	 * <li>all checkers from duck interface must comply for the original class
	 * <li>all methods from the duck interface must have their counterpart
	 * unless the {@link Checker checker} from annotation declares not to need
	 * them
	 * </ul>
	 * 
	 * @param <O>
	 *            the type of original object
	 * @param <D>
	 *            the duck interface
	 * @param original
	 *            the object to be adapted
	 * @param duck
	 *            the desired interface of adapted object
	 * @return whether the original object can be adapted to the desired
	 *         interface
	 */
	public static <O, D> boolean test(final O original, final Class<D> duck) {
		if (original == null) {
			return true;
		}
		return AdaptedFactory.adapt(getOriginalClass(original), duck)
				.canAdaptInstance();
	}

}
