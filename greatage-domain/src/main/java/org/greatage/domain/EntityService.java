/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;


import java.io.Serializable;
import java.util.List;

/**
 * This interface represents service for working with specified class of entities.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @see org.greatage.domain.EntityRepository
 * @since 1.0
 */
public interface EntityService<PK extends Serializable, E extends Entity<PK>> {

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
	 * Gets entities count.
	 *
	 * @return entities count
	 */
	int getEntitiesCount();

	/**
	 * Gets list of entities.
	 *
	 * @return list of entities or empty list if not found
	 */
	List<E> getEntities();

	/**
	 * Gets list of entities.
	 *
	 * @param pagination selection pagination
	 * @return list of entities or empty list if not found
	 */
	List<E> getEntities(Pagination pagination);

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
	void save(E entity);

	/**
	 * Updates detailed entity in repository.
	 *
	 * @param entity detailed entity
	 */
	void update(E entity);

	/**
	 * Inserts or updates detailed entity in repository.
	 *
	 * @param entity detailed entity
	 */
	void saveOrUpdate(E entity);

	/**
	 * Deletes detailed entity from repository
	 *
	 * @param entity detailed entity
	 */
	void delete(E entity);

}
