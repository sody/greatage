/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;

import java.io.Serializable;

/**
 * This interface represents filter processing logic to inject it into hibernate criteria.
 *
 * @author Ivan Khalopik
 * @see EntityRepository
 * @since 1.0
 */
public interface EntityFilterProcessor {

	/**
	 * Executes some logic to inject into hibernate criteria parameters stored in filter.
	 *
	 * @param criteria   hibernate criteria
	 * @param filter	 processed entity filter
	 * @param pagination pagination
	 * @param <PK>       type of entities primary key
	 * @param <E>        type of entities
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void process(EntityCriteria criteria, EntityFilter<PK, E> filter, Pagination pagination);

}
