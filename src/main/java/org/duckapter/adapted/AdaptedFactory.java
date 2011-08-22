package org.duckapter.adapted;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.duckapter.Adapted;
import org.duckapter.AdaptedClass;

import com.google.common.collect.MapMaker;

/**
 * The class {@link AdaptedFactory} is factory class for instances of
 * {@link Adapted} and {@link AdaptedClass} interfaces.
 * 
 * @author Vladimir Orany
 * 
 */
public final class AdaptedFactory {

	private static ThreadLocal<Set<String>> pending = new ThreadLocal<Set<String>>() {
		@Override
		protected Set<String> initialValue() {
			return new HashSet<String>();
		}
	};

	private AdaptedFactory() {
		// prevents instance creation and subtyping
	}

	/**
	 * Clear cached instances to free the memory or to get fresh results from
	 * {@link #adapt(Class, Class)} and {@link #adapt(Object, Class, Class)}
	 * methods.
	 */
	public static void clearCache() {
		cache.clear();
	}

	/**
	 * Creates an {@link Adapted} for selected parameters. The duck interface
	 * need not to be an interface but in that case returned instance will not
	 * be able of adaptation.
	 * 
	 * @param <O>
	 *            the type of original object
	 * @param <D>
	 *            the duck interface
	 * @param original
	 *            the original object
	 * @param originalClass
	 *            the class of original object
	 * @param duckInterface
	 *            the duck interface
	 * @return {@link Adapted} for selected parameters
	 */
	public static <O, D> Adapted<O, D> adapt(final O original,
			final Class<O> originalClass, final Class<D> duckInterface) {
		if (!duckInterface.isInterface()) {
			return new EmptyAdapted<O, D>(original, findAdaptedClass(
					originalClass, duckInterface));
		}
		return new AdaptedImpl<O, D>(original, findAdaptedClass(originalClass,
				duckInterface));
	}

	private static <O, D> String getCacheKey(final Class<O> originalClass,
			final Class<D> duckInterface) {
		return new StringBuilder(originalClass.getName()).append(":").append(
				duckInterface.getName()).toString();
	}

	/**
	 * Creates an {@link AdaptedClass} for selected parameters or use one from
	 * the pool. The duck interface need not to be an interface but in that case
	 * returned instance will not be able of adaptation.
	 * 
	 * @param <O>
	 *            the type of original object
	 * @param <D>
	 *            the duck interface the original object
	 * @param originalClass
	 *            the class of original object
	 * @param duckInterface
	 *            the duck interface
	 * @return {@link Adapted} for selected parameters
	 */
	public static <O, D> AdaptedClass<O, D> adapt(final Class<O> originalClass,
			final Class<D> duckInterface) {
		return findAdaptedClass(originalClass, duckInterface);
	}

	private static <O, D> AdaptedClass<O, D> findAdaptedClass(
			Class<O> originalClass, Class<D> duckInterface) {
		final String cacheKey = getCacheKey(originalClass, duckInterface);
		AdaptedClass<O, D> ac = getFromCache(cacheKey);
		if (ac == null) {
			if (pending.get().contains(cacheKey)) {
				return PendingAdaptedClass.nullInstance();
			} else {
				pending.get().add(cacheKey);
				if (duckInterface.isInterface()) {
					ac = new AdaptedClassImpl<O, D>(originalClass,
							duckInterface);
				} else {
					ac = new EmptyAdaptedClass<O, D>(originalClass,
							duckInterface);
				}
				cache.put(cacheKey, ac);
				pending.get().remove(cacheKey);
			}
		}
		return ac;
	}

	private static ConcurrentMap<String, AdaptedClass<?,?>> cache = new MapMaker().expiration(
			30, TimeUnit.MINUTES).makeMap();

	@SuppressWarnings("unchecked")
	private static <O, D> AdaptedClass<O, D> getFromCache(String s) {
		return (AdaptedClass<O, D>) cache.get(s);
	}

	static <O, D> void updateCacheInstance(AdaptedClass<O, D> replacement) {
		cache.putIfAbsent(getCacheKey(replacement.getOriginalClass(), replacement
				.getDuckInterface()), replacement);
	}

}
