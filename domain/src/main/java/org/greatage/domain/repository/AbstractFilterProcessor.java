/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;

import java.io.Serializable;

/**
 * This class represents abstract implementation of {@link org.greatage.domain.repository.EntityFilterProcessor} that works only
 * with filters of specified class.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @param <F>        type of entities filter
 */
public abstract class AbstractFilterProcessor<PK extends Serializable, E extends Entity<PK>, F extends EntityFilter<PK, E>>
		implements EntityFilterProcessor {

	private final Class<? extends Entity> supportedEntityClass;

	/**
	 * Constructor with supported entity class initialization.
	 *
	 * @param supportedEntityClass supported entity class
	 */
	protected AbstractFilterProcessor(Class<? extends Entity> supportedEntityClass) {
		this.supportedEntityClass = supportedEntityClass;
	}

	/**
	 * Executes some logic to inject into entity criteria parameters stored in filter, if specified filter are supported by
	 * this filter processor.
	 *
	 * @param criteria   entity criteria
	 * @param filter	 entity filter
	 * @param pagination pagination
	 * @param <SPK>      type of entities primary key
	 * @param <SE>       type of entities
	 */
	@SuppressWarnings({"unchecked"})
	public <SPK extends Serializable, SE extends Entity<SPK>>
	void process(EntityCriteria criteria, EntityFilter<SPK, SE> filter, Pagination pagination) {
		if (supports(filter)) {
			processFilter(criteria, (F) filter);
		}
	}

	/**
	 * Determines if this filter processor supports specified filter. If not, it will not be processed.
	 *
	 * @param filter entity filter
	 * @param <SPK>  type of entities primary key
	 * @param <SE>   type of entities
	 * @return true if filter is supported, false otherwise
	 */
	protected <SPK extends Serializable, SE extends Entity<SPK>>
	boolean supports(EntityFilter<SPK, SE> filter) {
		return supportedEntityClass.isAssignableFrom(filter.getEntityClass());
	}

	/**
	 * Executes some logic to inject into entity criteria parameters stored in filter.
	 *
	 * @param criteria entity criteria
	 * @param filter   processed entity filter
	 */
	protected abstract void processFilter(EntityCriteria criteria, F filter);

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("FilterProcessor(");
		sb.append(supportedEntityClass);
		sb.append(")");
		return sb.toString();
	}
}
