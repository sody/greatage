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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This interface represents generic repository for working with all entities.
 *
 * @author Ivan Khalopik
 * @see EntityService
 * @since 1.0
 */
public interface EntityRepository {

	/**
	 * Gets count of entities selected by filter.
	 *
	 * @param filter selection filter (not null)
	 * @param <PK>   type of entities primary key
	 * @param <E>    type of entities
	 * @return count of entities selected by filter
	 */
	<PK extends Serializable, E extends Entity<PK>>
	int count(EntityFilter<PK, E> filter);

	/**
	 * Gets list of entities selected by filter.
	 *
	 * @param filter	 selection filter (not null)
	 * @param pagination selection pagination(not null)
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 * @return list of entities selected by filter or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<E> find(EntityFilter<PK, E> filter, Pagination pagination);

	/**
	 * Gets list of entity`s pks selected by filter.
	 *
	 * @param filter	 selection filter (not null)
	 * @param pagination selection pagination(not null)
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 * @return list of entity`s pks selected by filter or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(EntityFilter<PK, E> filter, Pagination pagination);

	/**
	 * Gets list of entity`s value objects (map property->value) selected by filter according to projection options.
	 * Projection options looks like map (property name->property path).
	 *
	 * @param filter	 selection filter (not null)
	 * @param projection projection options (not null)
	 * @param pagination selection pagination(not null)
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 * @return list of entity`s value objects selected by filter or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(EntityFilter<PK, E> filter, Map<String, String> projection, Pagination pagination);

	/**
	 * Gets unique entity selected by filter.
	 *
	 * @param filter selection filter (not null)
	 * @param <PK>   type of entity primary key
	 * @param <E>    type of entity
	 * @return first found entity selected by filter or null if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	E findUnique(EntityFilter<PK, E> filter);


	/**
	 * Gets count of entities selected by entityClass.
	 *
	 * @param entityClass entity class (not null)
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return count of entities selected by entityClass
	 */
	<PK extends Serializable, E extends Entity<PK>>
	int count(Class<E> entityClass);

	/**
	 * Gets list of entities selected by entityClass.
	 *
	 * @param entityClass entity class (not null)
	 * @param pagination  selection pagination (not null)
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return list of entities selected by entityClass or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<E> find(Class<E> entityClass, Pagination pagination);

	/**
	 * Gets list of entity`s pks selected by entityClass.
	 *
	 * @param entityClass entity class (not null)
	 * @param pagination  selection pagination (not null)
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return list of entity`s pks selected by entityClass or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<PK> findKeys(Class<E> entityClass, Pagination pagination);

	/**
	 * Gets list of entity`s value objects (map property->value) selected by entityClass according to projection options.
	 * Projection options looks like map (property name->property path).
	 *
	 * @param entityClass entity class (not null)
	 * @param projection  projection options (not null)
	 * @param pagination  selection pagination(not null)
	 * @param <PK>        type of entities primary key
	 * @param <E>         type of entities
	 * @return list of entity`s value objects selected by entityClass or empty list if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	List<Map<String, Object>> findValueObjects(Class<E> entityClass, Map<String, String> projection, Pagination pagination);

	/**
	 * Gets detailed entity by pk.
	 *
	 * @param entityClass entity class (not null)
	 * @param pk		  entity pk (not null)
	 * @param <PK>        type of entity primary key
	 * @param <E>         type of entity
	 * @return detailed entity by pk or null if not found
	 */
	<PK extends Serializable, E extends Entity<PK>>
	E get(Class<E> entityClass, PK pk);

	/**
	 * Creates entity instance.
	 *
	 * @param entityClass entity class (not null)
	 * @return entity instance
	 */
	<PK extends Serializable, E extends Entity<PK>>
	E create(Class<E> entityClass);

	/**
	 * Makes entity persistent by saving it into repository.
	 *
	 * @param entity entity
	 * @param <PK>   type of entity primary key
	 * @param <E>    type of entity
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void save(E entity);

	/**
	 * Updates entity state in repository.
	 *
	 * @param entity entity
	 * @param <PK>   type of entity primary key
	 * @param <E>    type of entity
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void update(E entity);

	/**
	 * Makes entity persistent if it is not or updates entity state in repository otherwise.
	 *
	 * @param entity entity
	 * @param <PK>   type of entity primary key
	 * @param <E>    type of entity
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void saveOrUpdate(E entity);

	/**
	 * Deletes entity from repository.
	 *
	 * @param entity entity
	 * @param <PK>   type of entity primary key
	 * @param <E>    type of entity
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void delete(E entity);

}
