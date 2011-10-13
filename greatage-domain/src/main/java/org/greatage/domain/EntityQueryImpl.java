/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.domain;

import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityQueryImpl<PK extends Serializable, E extends Entity<PK>, Q extends EntityQuery<PK, E, Q>>
		implements EntityFilter<PK, E>, EntityQuery<PK, E, Q> {

	private final Class<E> entityClass;
	private EntityRepository repository;
	private String queryString;
	private List<PK> includeKeys;
	private List<PK> excludeKeys;

	public EntityQueryImpl(final EntityRepository repository, final Class<E> entityClass) {
		this.repository = repository;
		this.entityClass = entityClass;
	}

	/**
	 * Gets entity class.
	 *
	 * @return entity class
	 */
	public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * Gets search query string for entity full-text search.
	 *
	 * @return search query string for entity full-text search
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Gets collection of entity primary keys that will be included into select result.
	 *
	 * @return collection of entity primary keys that will be included into select result
	 */
	public Collection<PK> getIncludeKeys() {
		return includeKeys;
	}

	/**
	 * Gets collection of entity primary keys that will be excluded from select result.
	 *
	 * @return collection of entity primary keys that will be excluded from select result
	 */
	public Collection<PK> getExcludeKeys() {
		return excludeKeys;
	}

	/**
	 * Sets search query string for entity full-text search.
	 *
	 * @param queryString search query string for entity full-text search
	 * @return the same query instance
	 */
	public Q setQueryString(final String queryString) {
		this.queryString = queryString;
		return query();
	}

	/**
	 * Sets collection of entity primary keys that will be included into select result.
	 *
	 * @param keys collection of entity primary keys that will be included into select result
	 * @return the same query instance
	 */
	public Q includeKeys(final Collection<PK> keys) {
		if (includeKeys == null) {
			includeKeys = new ArrayList<PK>();
		}
		includeKeys.addAll(keys);
		return query();
	}

	/**
	 * Sets collection of entity primary keys that will be excluded from select result.
	 *
	 * @param keys collection of entity primary keys that will be excluded from select result
	 * @return the same query instance
	 */
	public Q excludeKeys(final Collection<PK> keys) {
		if (excludeKeys == null) {
			excludeKeys = new ArrayList<PK>();
		}
		excludeKeys.addAll(keys);
		return query();
	}

	/**
	 * Gets entities count selected by this query instance.
	 *
	 * @return entities count selected by this query instance
	 */
	public int count() {
		return repository().count(this);
	}

	/**
	 * Gets paginated list of entities selected by this query instance.
	 *
	 * @param pagination selection pagination
	 * @return paginated list of entities selected by this query instance or empty list if not found
	 */
	public List<E> list(final Pagination pagination) {
		return repository().find(this, pagination);
	}

	/**
	 * Gets list of entities selected by this query instance.
	 *
	 * @return list of entities selected by this query instance or empty list if not found
	 */
	public List<E> list() {
		return repository().find(this, Pagination.ALL);
	}

	/**
	 * Gets unique entity selected by this query instance.
	 *
	 * @return unique entity selected by this query instance
	 */
	public E unique() {
		return repository().findUnique(this);
	}


	/**
	 * Gets EntityRepository instance assigned to this query.
	 *
	 * @return assigned EntityRepository instance
	 */
	protected EntityRepository repository() {
		if (repository == null) {
			throw new IllegalStateException("This query is not assigned to repository.");
		}
		return repository;
	}

	/**
	 * Gets this query instance casted to Q generic parameter.
	 *
	 * @return the same query instance
	 */
	@SuppressWarnings({"unchecked"})
	protected Q query() {
		return (Q) this;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("class", entityClass);
		builder.append("queryString", queryString);
		return builder.toString();
	}
}
