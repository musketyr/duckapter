package org.duckapter.adapter;

import org.duckapter.InvocationAdapter;
import org.duckapter.annotation.DoesNotHave;
import org.duckapter.checker.DoesNotHaveChecker;

/**
 * The adapter for {@link DoesNotHave} annotation and {@link DoesNotHaveChecker}
 * . Collects the best suitable adapter for particular duck method and inverts
 * the results of its {@link #isInvocableOnClass()} and
 * {@link #isInvocableOnInstance()} methods.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.annotation.DoesNotHave
 * @see org.duckapter.checker.DoesNotHaveChecker
 */
public class DoesNotHaveAdapter extends EmptyAdapter {

	private InvocationAdapter inverted = null;

	/**
	 * @param returnType
	 *            the return type
	 */
	public DoesNotHaveAdapter(Class<?> returnType) {
		super(returnType);
	}

	@Override
	public InvocationAdapter orMerge(InvocationAdapter other) {
		if (inverted == null) {
			inverted = InvocationAdapters.MIN;
		}
		if (other instanceof DoesNotHaveAdapter) {
			inverted = inverted.orMerge(((DoesNotHaveAdapter) other).inverted);
		} else {
			inverted = inverted.orMerge(other);
		}
		return this;
	}

	@Override
	public InvocationAdapter andMerge(InvocationAdapter other) {
		if (inverted == null) {
			inverted = InvocationAdapters.MAX;
		}
		if (other instanceof DoesNotHaveAdapter) {
			inverted = inverted.andMerge(((DoesNotHaveAdapter) other).inverted);
		} else {
			inverted = inverted.andMerge(other);
		}
		return this;
	}

	@Override
	public int getPriority() {
		return InvocationAdaptersPriorities.HAS_NO;
	}

	@Override
	public boolean isInvocableOnClass() {
		return !inverted.isInvocableOnClass();
	}

	@Override
	public boolean isInvocableOnInstance() {
		return !inverted.isInvocableOnInstance();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((inverted == null) ? 0 : inverted.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoesNotHaveAdapter other = (DoesNotHaveAdapter) obj;
		if (inverted == null) {
			if (other.inverted != null)
				return false;
		} else if (!inverted.equals(other.inverted))
			return false;
		return true;
	}

}
