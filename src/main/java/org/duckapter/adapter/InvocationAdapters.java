package org.duckapter.adapter;

import java.lang.reflect.Method;

import org.duckapter.InvocationAdapter;

/**
 * Utility class for {@link InvocationAdapter invocation adapters}. Provides
 * some default implementation of marking (logical) adapters. Also provides some
 * useful methods which can be used by other {@link InvocationAdapter}
 * implemenations.
 * 
 * @author Vladimir Orany
 * 
 */
public enum InvocationAdapters implements InvocationAdapter {
	/**
	 * The adapter which symbolizes the successful check without being capable
	 * to adapt the invocation properly. Its typically used as a substitute of
	 * boolean <code>true</code> value where there are other checkers which can
	 * adapt the invocation more properly.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	OK(InvocationAdaptersPriorities.OK) {
		@Override
		public boolean isInvocableOnClass() {
			return true;
		}

		@Override
		public boolean isInvocableOnInstance() {
			return true;
		}
	},

	/**
	 * The adapter which symbolizes the successful check without being capable
	 * to adapt the invocation properly. Its typically used as a substitute of
	 * boolean <code>true</code> value where there are no way how to adapt the
	 * invocation properly.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	DISCRIMINATOR(InvocationAdaptersPriorities.HAS_NO) {
		@Override
		public boolean isInvocableOnClass() {
			return true;
		}

		@Override
		public boolean isInvocableOnInstance() {
			return true;
		}
	},

	/**
	 * The adapter with the maximal priority. Used as the default value during
	 * andMerge cycles.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	MAX(InvocationAdaptersPriorities.MAX) {
		@Override
		public InvocationAdapter orMerge(InvocationAdapter other) {
			return this;
		}
	},

	/**
	 * The adapter with the minimal priority. Used as the default value during
	 * orMerge cycles.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	MIN(InvocationAdaptersPriorities.MIN) {
		@Override
		public InvocationAdapter andMerge(InvocationAdapter other) {
			return this;
		}
	},

	/**
	 * The adapter which symbolizes the unsuccessful check. Its typically used
	 * as a substitute of boolean <code>false</code> value. It signalizes that
	 * the element cannot be adapted at all.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	NULL(InvocationAdaptersPriorities.NONE) {
		@Override
		public Object invoke(Object obj, Object[] args) throws Throwable {
			throw new UnsupportedOperationException(
					"Method not supported by the duck class!");
		}
	};

	private final int priority;

	private InvocationAdapters(int priority) {
		this.priority = priority;
	}

	/**
	 * Test the adapter for <code>null</code> value. The value
	 * {@link InvocationAdapters#NULL} is also taken as <code>null</code> value.
	 * 
	 * @param adapter
	 *            the adapter to be tested
	 * @return whether the adapter is <code>null</code> or {@link #NULL}
	 */
	public static boolean isNull(InvocationAdapter adapter) {
		return adapter == null || NULL == adapter;
	}

	/**
	 * Test the adapter for non-null value. The value
	 * {@link InvocationAdapters#NULL} is also taken as <code>null</code> value.
	 * 
	 * @param adapter
	 *            the adapter to be tested
	 * @return whether the adapter is not <code>null</code> and not
	 *         {@link #NULL}
	 */
	public static boolean notNull(InvocationAdapter adapter) {
		return !isNull(adapter);
	}

	/**
	 * This method is used to safely call methods on the invocation adapter if
	 * the first value {@link #isNull(InvocationAdapter) is null} than the
	 * second one is used as the result. Otherwise the first adapter is
	 * returned.
	 * 
	 * @param adapter
	 *            the adapter which might be <code>null</code>
	 * @param defaultAdapter
	 *            the adapter which is used if the first one is
	 *            <code>null</code>
	 * @return the first adapter if it is not <code>null</code> the second one
	 *         otherwise
	 */
	public static InvocationAdapter safe(InvocationAdapter adapter,
			InvocationAdapter defaultAdapter) {
		if (adapter == null) {
			return defaultAdapter;
		}
		return adapter;
	}

	/**
	 * Merges the invocation adapters using their priority. Returns the one with
	 * the highest priority
	 * 
	 * @param first
	 *            the first adapter
	 * @param others
	 *            the other adapters
	 * @return the one with the highest priority
	 */
	public static InvocationAdapter orMerge(InvocationAdapter first,
			InvocationAdapter... others) {
		InvocationAdapter highest = first;
		for (InvocationAdapter invocationAdapter : others) {
			if (invocationAdapter.getPriority() > highest.getPriority()) {
				highest = invocationAdapter;
			}
		}
		return highest;
	}

	/**
	 * Merges the invocation adapters using their priority. Returns the one with
	 * the lowest priority
	 * 
	 * @param first
	 *            the first adapter
	 * @param others
	 *            the other adapters
	 * @return the one with the lowest priority
	 */
	public static InvocationAdapter andMerge(InvocationAdapter first,
			InvocationAdapter... others) {
		InvocationAdapter lowest = first;
		for (InvocationAdapter invocationAdapter : others) {
			if (invocationAdapter.getPriority() < lowest.getPriority()) {
				lowest = invocationAdapter;
			}
		}
		return lowest;
	}

	/**
	 * Merges the invocation adapters using their priority. Returns the one with
	 * the highest priority
	 * 
	 * @param first
	 *            the first adapter
	 * @param other
	 *            the other adapter
	 * @return the one with the highest priority
	 */
	public static InvocationAdapter orMerge(InvocationAdapter first,
			InvocationAdapter other) {
		if (other.getPriority() > first.getPriority()) {
			return other;
		} else {
			return first;
		}
	}

	/**
	 * Merges the invocation adapters using their priority. Returns the one with
	 * the lowest priority
	 * 
	 * @param first
	 *            the first adapter
	 * @param other
	 *            the other adapter
	 * @return the one with the lowest priority
	 */
	public static InvocationAdapter andMerge(InvocationAdapter first,
			InvocationAdapter other) {
		if (other == null) {
			return first;
		}
		if (other.getPriority() < first.getPriority()) {
			return other;
		} else {
			return first;
		}
	}

	public int getPriority() {
		return priority;
	}

	public boolean isInvocableOnClass() {
		return false;
	}

	public boolean isInvocableOnInstance() {
		return false;
	}

	public Object invoke(Object obj, Object[] args) throws Throwable {
		return null;
	}

	public InvocationAdapter orMerge(InvocationAdapter other) {
		return orMerge(this, other);
	}

	public InvocationAdapter andMerge(InvocationAdapter other) {
		return andMerge(this, other);
	}
	
	public Method getDuckMethod() {
		return Default.METHOD;
	}
	
	public void setDuckMethod(Method method) {
		// does nothing
	}

}
