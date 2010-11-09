/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import org.greatage.domain.repository.sql.SqlProperty;
import org.greatage.util.DescriptionBuilder;

import javax.jdo.Query;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoProperty extends SqlProperty {
	private final String property;
	private final String fullProperty;
	private final Query query;

	public JdoProperty(final Query query, final String path, final String property) {
		this.query = query;
		this.property = property;
		this.fullProperty = path != null ? path + "." + property : property;
	}

	public String getProperty() {
		return property;
	}

	protected String property() {
		return fullProperty;
	}

	public void sort() {
		sort(true, true);
	}

	public void sort(final boolean ascending) {
		sort(ascending, true);
	}

	public void sort(final boolean ascending, final boolean ignoreCase) {
		query.setOrdering(property());
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(property);
		return builder.toString();
	}
}
