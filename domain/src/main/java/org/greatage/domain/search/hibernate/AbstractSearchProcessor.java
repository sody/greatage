/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.repository.EntityFilter;
import org.apache.lucene.search.BooleanQuery;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * This class represents abstract implementation of {@link SearchProcessor} that works only with filters of specified
 * class.
 *
 * @author Ivan Khalopik
 */
public abstract class AbstractSearchProcessor implements SearchProcessor {
	private final Class<? extends Entity> supportedEntityClass;

	/**
	 * Constructor with supported entity class initialization.
	 *
	 * @param supportedEntityClass supported entity class
	 */
	protected AbstractSearchProcessor(Class<? extends Entity> supportedEntityClass) {
		this.supportedEntityClass = supportedEntityClass;
	}

	/**
	 * Executes some logic to inject into lucene query parameters stored in filter, if specified filter are supported by
	 * this search processor.
	 *
	 * @param filter entity filter
	 * @param <PK>   type of entities primary key
	 * @param <E>    type of entities
	 */
	public <PK extends Serializable, E extends Entity<PK>>
	void process(BooleanQuery query, EntityFilter<PK, E> filter) {
		if (supports(filter)) {
			process(query, filter.getQueryString());
		}
	}

	/**
	 * Determines if this search processor supports specified filter. If not, it will not be processed.
	 *
	 * @param filter entity filter
	 * @param <PK>   type of entities primary key
	 * @param <E>    type of entities
	 * @return true if filter is supported, false otherwise
	 */
	protected <PK extends Serializable, E extends Entity<PK>>
	boolean supports(EntityFilter<PK, E> filter) {
		return StringUtils.hasText(filter.getQueryString()) && supportedEntityClass.isAssignableFrom(filter.getEntityClass());
	}

	/**
	 * Executes some logic to inject into lucene query parsed query string.
	 *
	 * @param query	   lucene query
	 * @param queryString queryString
	 */
	protected abstract void process(BooleanQuery query, String queryString);

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SearchProcessor(");
		sb.append(supportedEntityClass);
		sb.append(")");
		return sb.toString();
	}
}
