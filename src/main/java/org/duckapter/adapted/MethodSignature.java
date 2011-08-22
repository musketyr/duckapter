/**
 * 
 */
package org.duckapter.adapted;

import java.util.Arrays;

final class MethodSignature {
	private final String name;
	private final Class<?>[] parameterTypes;
	private final int hashCode;

	public MethodSignature(Class<?>[] parameterTypes, String name) {
		this.parameterTypes = parameterTypes;
		this.name = name;
		this.hashCode = computeHash();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodSignature other = (MethodSignature) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(parameterTypes, other.parameterTypes))
			return false;
		return true;
	}

	@Override
	public int hashCode() { return hashCode; };
	
	private final int computeHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameterTypes);
		return result;
	}

}