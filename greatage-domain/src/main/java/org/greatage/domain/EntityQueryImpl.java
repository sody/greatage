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
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityQueryImpl<PK extends Serializable, E extends Entity<PK>> implements EntityQuery<PK, E> {
	private final Class<E> entityClass;
	private final Criteria<PK, E> criteria;
	private EntityRepository repository;

	public EntityQueryImpl(final EntityRepository repository,
						   final Class<E> entityClass,
						   final Criteria<PK, E> criteria) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.criteria = criteria;
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
	 * Gets entities count selected by this query instance.
	 *
	 * @return entities count selected by this query instance
	 */
	public int count() {
		return repository.count(entityClass, criteria);
	}

	/**
	 * Gets paginated list of entities selected by this query instance.
	 *
	 * @param pagination selection pagination
	 * @return paginated list of entities selected by this query instance or empty list if not found
	 */
	public List<E> list(final Pagination pagination) {
		return repository.find(entityClass, criteria, pagination);
	}

	/**
	 * Gets list of entities selected by this query instance.
	 *
	 * @return list of entities selected by this query instance or empty list if not found
	 */
	public List<E> list() {
		return repository.find(entityClass, criteria, Pagination.ALL);
	}

	/**
	 * Gets unique entity selected by this query instance.
	 *
	 * @return unique entity selected by this query instance
	 */
	public E unique() {
		return repository.findUnique(entityClass, criteria);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entityClass);
		builder.append(criteria);
		return builder.toString();
	}
}
