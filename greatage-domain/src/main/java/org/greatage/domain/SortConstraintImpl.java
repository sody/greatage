/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SortConstraintImpl implements SortConstraint {
	private String property;
	private boolean ascending;
	private boolean ignoreCase;

	public SortConstraintImpl(final String property, final boolean ascending, final boolean ignoreCase) {
		this.property = property;
		this.ascending = ascending;
		this.ignoreCase = ignoreCase;
	}

	public String getProperty() {
		return property;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public boolean isAscending() {
		return ascending;
	}
}
