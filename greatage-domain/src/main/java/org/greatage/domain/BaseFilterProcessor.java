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

import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;

/**
 * This class represents implementation of {@link EntityFilterProcessor} that processes existing, includeKeys and
 * excludeKeys filter parameters.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseFilterProcessor implements EntityFilterProcessor {

	public <PK extends Serializable, E extends Entity<PK>>
	void process(final EntityCriteria criteria, final EntityFilter<PK, E> filter, final Pagination pagination) {
		processPagination(criteria, pagination);
		processFilter(criteria, filter);
	}

	protected void processPagination(final EntityCriteria criteria, final Pagination pagination) {
		criteria.setPagination(pagination);

		if (pagination.getSortConstraints() != null) {
			for (SortConstraint definition : pagination.getSortConstraints()) {
				processSort(criteria, definition);
			}
		}
	}

	protected void processSort(final EntityCriteria criteria, final SortConstraint sort) {
		final String property = sort.getProperty();
		criteria.getProperty(property).sort(sort.isAscending(), sort.isIgnoreCase());
	}

	protected <PK extends Serializable, E extends Entity<PK>>
	void processFilter(final EntityCriteria criteria, final EntityFilter<PK, E> filter) {
		if (filter.getIncludeKeys() != null) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getIncludeKeys()));
		}

		if (filter.getExcludeKeys() != null && !filter.getExcludeKeys().isEmpty()) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getExcludeKeys()).not());
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		return builder.toString();
	}
}