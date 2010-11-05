/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search;

import org.greatage.domain.Entity;
import org.greatage.domain.repository.EntityFilter;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents full-text search engine for searching entity`s pks.
 *
 * @author Ivan Khalopik
 * @see org.greatage.domain.repository.EntityRepository
 */
public interface SearchEngine {

	<PK extends Serializable, E extends Entity<PK>>
	void index(final Class<E> entityClass);

	/**
	 * Searches for entity`s pks by entityClass and queryString.
	 *
	 * @param entityClass entity class for searching
	 * @param queryString query string for searching
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return list of entity`s pks selected by queryString, empty list if not found or null if search is not supported
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<PK> search(Class<E> entityClass, String queryString);

	/**
	 * Searches for entity`s pks by filter.
	 *
	 * @param filter entity filter
	 * @param <PK>   type of entities primary key
	 * @param <E>    type of entities
	 * @return list of entity`s pks selected by filter, empty list if not found or null if search is not supported
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<PK> search(EntityFilter<PK, E> filter);
}
