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

/**
 * This interface represents generic repository for working with all entities.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Repository {

	/**
	 * Gets detailed entity by pk.
	 *
	 * @param entityClass entity class (not null)
	 * @param pk          entity pk (not null)
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

	<PK extends Serializable, E extends Entity<PK>>
	Query<PK, E> query(Class<E> entityClass);


	interface Query<PK extends Serializable, E extends Entity<PK>> {

		Query<PK, E> filter(Criteria<PK, E> criteria);

		Query<PK, E> fetch(Property property);

		Query<PK, E> sort(Property property, boolean ascending, boolean ignoreCase);

		Query<PK, E> paginate(int start, int count);


		long count();

		List<E> list();

		List<PK> keys();

		E unique();
	}

	interface Property {

		String getPath();

		String getProperty();
	}

	interface Criteria<PK extends Serializable, E extends Entity<PK>> {

		Criteria<PK, E> and(Criteria<PK, E> criteria);

		Criteria<PK, E> or(Criteria<PK, E> criteria);

		Criteria<PK, E> not();
	}
}
