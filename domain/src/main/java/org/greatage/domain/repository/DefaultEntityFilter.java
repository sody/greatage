/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultEntityFilter<PK extends Serializable, E extends Entity<PK>> implements EntityFilter<PK, E> {
	private final Class<E> entityClass;
	private String queryString;

	public DefaultEntityFilter(final Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public DefaultEntityFilter(final Class<E> entityClass, final String queryString) {
		this.entityClass = entityClass;
		this.queryString = queryString;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public Collection<PK> getIncludeKeys() {
		return null;
	}

	public Collection<PK> getExcludeKeys() {
		return null;
	}

	public String getQueryString() {
		return queryString;
	}
}
