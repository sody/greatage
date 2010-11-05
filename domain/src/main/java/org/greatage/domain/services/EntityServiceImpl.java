/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.services;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.PaginationBuilder;
import org.greatage.domain.repository.EntityQuery;
import org.greatage.domain.repository.EntityRepository;
import org.greatage.util.ReflectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents default implementation of {@link EntityService}.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @param <Q>        type of entities query
 */
public class EntityServiceImpl<PK extends Serializable, E extends Entity<PK>, Q extends EntityQuery<PK, E, Q>>
		implements EntityService<PK, E> {

	private final EntityRepository repository;
	private final Class<E> entityClass;
	private final Class<Q> queryClass;
	private final String entityName;

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 */
	public EntityServiceImpl(EntityRepository repository, Class<E> entityClass) {
		this(repository, entityClass, null);
	}

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 * @param queryClass  entity filter class
	 */
	public EntityServiceImpl(EntityRepository repository, Class<E> entityClass, Class<Q> queryClass) {
		this(repository, entityClass, queryClass, null);
	}

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 * @param queryClass  entity filter class
	 * @param entityName  entity name
	 */
	public EntityServiceImpl(EntityRepository repository, Class<E> entityClass, Class<Q> queryClass, String entityName) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.queryClass = queryClass;
		this.entityName = entityName != null ? entityName : entityClass.getName();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public E create() {
		return repository().create(getEntityClass());
	}

	@Transactional
	public void saveOrUpdate(E entity) {
		if (entity.isNew()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	@Transactional
	public void save(E entity) {
		repository().save(entity);
	}

	@Transactional
	public void update(E entity) {
		repository().update(entity);
	}

	@Transactional
	public void delete(E entity) {
		repository().delete(entity);
	}

	public E get(PK pk) {
		return repository().get(getEntityClass(), pk);
	}

	public int getEntitiesCount() {
		return createQuery().count();
	}

	public List<E> getEntities() {
		return createQuery().list();
	}

	public List<E> getEntities(Pagination pagination) {
		return createQuery().list(pagination);
	}

	/**
	 * Gets entity repository.
	 *
	 * @return entity repository
	 */
	protected EntityRepository repository() {
		return repository;
	}

	/**
	 * Gets default pagination for entities selection.
	 *
	 * @return default pagination for entities selection
	 */
	protected Pagination createDefaultPagination() {
		return PaginationBuilder.ALL;
	}

	/**
	 * Creates default filter for entities selection.
	 *
	 * @return default filter for entities selection
	 */
	protected Q createQuery() {
		return ReflectionUtils.newInstance(queryClass).assign(repository());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EntityService(");
		sb.append("class=").append(entityClass);
		sb.append(", name=").append(entityName);
		sb.append(")");
		return sb.toString();
	}
}
