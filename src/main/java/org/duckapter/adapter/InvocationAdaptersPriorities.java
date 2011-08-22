package org.duckapter.adapter;

/**
 * This class contains constants for priorities of the built-in invocation
 * adapters.
 * 
 * @author Vladimir Orany
 * 
 */
public final class InvocationAdaptersPriorities {

	/**
	 * The minimal value used in {@link InvocationAdapters#MIN}.
	 */
	public static final int MIN = Integer.MIN_VALUE;

	/**
	 * The value used in {@link OptionalAdapter}.
	 */
	public static final int OPTIONAL = Integer.MIN_VALUE + 500;

	/**
	 * The value used in {@link InvocationAdapters#NULL}.
	 */
	public static final int NONE = OPTIONAL + 500;

	/**
	 * The value used in {@link SetFieldAdapter} and {@link GetFieldAdapter}.
	 */
	public static final int FIELD = -10000;

	/**
	 * The value used in {@link ConstructorAdapter}.
	 */
	public static final int CONSTRUCTOR = -5000;

	/**
	 * The value used in {@link MethodAdapter}.
	 */
	public static final int METHOD = 0;

	/**
	 * The value used in {@link InvocationAdapters#DISCRIMINATOR}.
	 */
	public static final int WITH_ANY_PARAMS = 10000;

	/**
	 * The value used in {@link InvocationAdapters#OK}.
	 */
	public static final int OK = Integer.MAX_VALUE - 1000;

	/**
	 * The value used in {@link AllAdapter}.
	 */
	public static final int ALL = Integer.MAX_VALUE - 1;

	/**
	 * The value used in {@link DoesNotHaveAdapter}.
	 */
	public static final int HAS_NO = ALL;

	/**
	 * The maximal value used in {@link InvocationAdapters#MAX}.
	 */
	public static final int MAX = Integer.MAX_VALUE;

	private InvocationAdaptersPriorities() {
		// prevents instance creation and subtyping
	}

}
