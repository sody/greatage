/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.select;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public class SimpleSelectModel<V> extends LazySelectModel<V> {
	private final Collection<V> values;

	public SimpleSelectModel(Collection<V> values) {
		this.values = values; //todo: defense
	}

	@Override
	protected Collection<V> getValues() {
		return values;
	}
}
