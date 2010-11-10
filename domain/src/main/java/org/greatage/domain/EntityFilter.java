/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface represents base filter for selection entities from repositories.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @see EntityRepository
 * @see EntityService
 * @since 1.0
 */
public interface EntityFilter<PK extends Serializable, E extends Entity<PK>> {

	/**
	 * Gets entity class.
	 *
	 * @return entity class
	 */
	Class<E> getEntityClass();

	/**
	 * Gets collection of entity primary keys that will be included into select result.
	 *
	 * @return collection of entity primary keys that will be included into select result
	 */
	Collection<PK> getIncludeKeys();

	/**
	 * Gets collection of entity primary keys that will be excluded from select result.
	 *
	 * @return collection of entity primary keys that will be excluded from select result
	 */
	Collection<PK> getExcludeKeys();

	/**
	 * Gets search query string for entity full-text search.
	 *
	 * @return search query string for entity full-text search
	 */
	String getQueryString();

}
