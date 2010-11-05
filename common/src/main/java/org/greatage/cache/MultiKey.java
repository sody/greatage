package org.greatage.cache;

import java.util.Arrays;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MultiKey {
	private static final int PRIME = 31;

	private final Object[] values;
	private final int hashCode;

	public MultiKey(Object... values) {
		this.values = values;
		hashCode = PRIME * Arrays.hashCode(this.values);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		final MultiKey other = (MultiKey) obj;
		return Arrays.equals(values, other.values);
	}

	@Override
	public String toString() {
		return "MultiKey[" + Arrays.toString(values) + "]";
	}

}
