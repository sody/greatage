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

package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.EntityService;
import org.greatage.domain.Repository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;

/**
 * This class represents default implementation of {@link org.greatage.domain.EntityService}.
 *
 * @param <PK> type of entities primary key
 * @param <E>  type of entities
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityServiceImpl<PK extends Serializable, E extends Entity<PK>>
		implements EntityService<PK, E> {

	private final Repository repository;
	private final Class<E> entityClass;
	private final String entityName;

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 */
	public EntityServiceImpl(final Repository repository, final Class<E> entityClass) {
		this(repository, entityClass, null);
	}

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 * @param entityName  entity name
	 */
	public EntityServiceImpl(final Repository repository, final Class<E> entityClass, final String entityName) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.entityName = entityName != null ? entityName : entityClass.getName();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public E create() {
		return repository.create(getEntityClass());
	}

	@Transactional
	public void saveOrUpdate(final E entity) {
		if (entity.isNew()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	@Transactional
	public void save(final E entity) {
		repository.save(entity);
	}

	@Transactional
	public void update(final E entity) {
		repository.update(entity);
	}

	@Transactional
	public void delete(final E entity) {
		repository.delete(entity);
	}

	public E get(final PK pk) {
		return repository.get(getEntityClass(), pk);
	}

	public Repository.Query<PK, E> query() {
		return repository.query(entityClass);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("class", entityClass);
		builder.append("name", entityName);
		return builder.toString();
	}
}