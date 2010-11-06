/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import org.greatage.domain.repository.sql.SqlProperty;

import javax.jdo.Query;

/**
 * @author Ivan Khalopik
 */
public class JdoEntityProperty extends SqlProperty {
	private final String property;
	private final String fullProperty;
	private final Query query;

	public JdoEntityProperty(Query query, String path, String property) {
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

	public void sort(boolean ascending) {
		sort(ascending, true);
	}

	public void sort(boolean ascending, boolean ignoreCase) {
		query.setOrdering(property());
	}
}
