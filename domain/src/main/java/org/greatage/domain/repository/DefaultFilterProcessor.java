/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.springframework.beans.support.SortDefinition;

import java.io.Serializable;

/**
 * This class represents implementation if {@link org.greatage.domain.repository.EntityFilterProcessor} that processes existing,
 * includeKeys and excludeKeys filter parameters.
 *
 * @author Ivan Khalopik
 */
public class DefaultFilterProcessor implements EntityFilterProcessor {

	public <PK extends Serializable, E extends Entity<PK>>
	void process(EntityCriteria criteria, EntityFilter<PK, E> filter, Pagination pagination) {
		processPagination(criteria, pagination);
		processFilter(criteria, filter);
	}

	protected void processPagination(EntityCriteria criteria, Pagination pagination) {
		criteria.setPagination(pagination);

		if (pagination.getSortDefinitions() != null) {
			for (SortDefinition definition : pagination.getSortDefinitions()) {
				processSort(criteria, definition);
			}
		}
	}

	protected void processSort(EntityCriteria criteria, SortDefinition sort) {
		final String property = sort.getProperty();
		criteria.getProperty(property).sort(sort.isAscending(), sort.isIgnoreCase());
	}

	protected <PK extends Serializable, E extends Entity<PK>>
	void processFilter(EntityCriteria criteria, EntityFilter<PK, E> filter) {
		if (filter.getIncludeKeys() != null) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getIncludeKeys()));
		}

		if (filter.getExcludeKeys() != null && !filter.getExcludeKeys().isEmpty()) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getExcludeKeys()).not());
		}
	}

	@Override
	public String toString() {
		return "DefaultFilterProcessor";
	}
}