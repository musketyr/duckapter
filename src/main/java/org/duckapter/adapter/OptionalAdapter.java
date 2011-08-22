package org.duckapter.adapter;

import org.duckapter.InvocationAdapter;

/**
 * The adapter for {@link org.duckapter.annotation.Optional} annotation and
 * {@link OptionalAdapter}. It is always invokable because the annotation does
 * not need to find out its counterpart.
 * 
 * @author Vladimir Orany
 * @see org.duckapter.annotation.Optional
 * @see org.duckapter.checker.OptionalChecker
 */
public class OptionalAdapter extends EmptyAdapter implements InvocationAdapter {

	/**
	 * @param returnType the return type
	 */
	public OptionalAdapter(Class<?> returnType) {
		super(returnType);
	}

	@Override
	public boolean isInvocableOnClass() {
		return true;
	}

	@Override
	public boolean isInvocableOnInstance() {
		return true;
	}

	@Override
	public int getPriority() {
		return InvocationAdaptersPriorities.OPTIONAL;
	}

}
