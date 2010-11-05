/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents implementation of {@link org.greatage.domain.repository.EntityFilterProcessor} that combines logic of
 * child filter processors.
 *
 * @author Ivan Khalopik
 */
public class CompositeFilterProcessor implements EntityFilterProcessor {
	private final List<EntityFilterProcessor> processors;

	/**
	 * Constructor with child filter processors initialization.
	 *
	 * @param processors child filter processors
	 */
	public CompositeFilterProcessor(List<EntityFilterProcessor> processors) {
		this.processors = processors;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CompositeFilterProcessor[");
		for (EntityFilterProcessor processor : processors) {
			sb.append(processor).append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void process(EntityCriteria criteria, EntityFilter<PK, E> filter, Pagination pagination) {
		for (EntityFilterProcessor processor : processors) {
			processor.process(criteria, filter, pagination);
		}
	}
}
