/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

import java.util.Arrays;

/**
 * This class represents key for using inside cache implementation that combines multiple values to form a single
 * composite key.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MultiKey {
	private static final int PRIME = 31;

	private final Object[] values;
	private final int hashCode;

	/**
	 * Creates new instance of multi key from the provided values. Values have to be a good map keys, immutable, with
	 * proper implementations of equals() and hashCode().
	 *
	 * @param values composite key parts
	 */
	public MultiKey(final Object... values) {
		this.values = values;
		hashCode = PRIME * Arrays.hashCode(this.values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final MultiKey other = (MultiKey) obj;
		return Arrays.equals(values, other.values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("values", Arrays.toString(values));
		return builder.toString();
	}
}
