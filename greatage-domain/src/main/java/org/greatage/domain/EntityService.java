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


import org.greatage.domain.annotations.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents service for working with specified class of entities.
 *
 * @param <PK> type of entities primary key
 * @param <E>  type of entities
 * @author Ivan Khalopik
 * @see org.greatage.domain.EntityRepository
 * @since 1.0
 */
public interface EntityService<PK extends Serializable, E extends Entity<PK>, Q extends EntityQuery<PK, E, Q>> {

	/**
	 * Gets entity class, service working with.
	 *
	 * @return entity class
	 */
	Class<E> getEntityClass();

	/**
	 * Gets entity name, service working with.
	 *
	 * @return entity name
	 */
	String getEntityName();

	/**
	 * Gets detailed entity selected by primary key.
	 *
	 * @param pk entity primary key
	 * @return detailed entity selected by primary key
	 */
	E get(PK pk);

	/**
	 * Creates detailed entity filled with default values.
	 *
	 * @return detailed entity filled with default values
	 */
	E create();

	/**
	 * Inserts detailed entity into repository.
	 *
	 * @param entity detailed entity
	 */
	@Transactional
	void save(E entity);

	/**
	 * Updates detailed entity in repository.
	 *
	 * @param entity detailed entity
	 */
	@Transactional
	void update(E entity);

	/**
	 * Inserts or updates detailed entity in repository.
	 *
	 * @param entity detailed entity
	 */
	@Transactional
	void saveOrUpdate(E entity);

	/**
	 * Deletes detailed entity from repository
	 *
	 * @param entity detailed entity
	 */
	@Transactional
	void delete(E entity);

	Q query();

}
