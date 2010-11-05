/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.springframework.beans.support.SortDefinition;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * This interface  represents pagination info for selections from repository and services.
 *
 * @author Ivan Khalopik
 * @see org.greatage.domain.PaginationBuilder
 * @see org.greatage.domain.services.EntityService
 * @see org.greatage.domain.repository.EntityRepository
 */
public interface Pagination extends Serializable {

	/**
	 * Gets selection start position.
	 *
	 * @return selection start position
	 */
	int getStart();

	/**
	 * Gets selection count.
	 *
	 * @return selection count
	 */
	int getCount();

	/**
	 * Gets selection sort orders.
	 *
	 * @return selection sort orders or empty list if without sortings
	 */
	Collection<SortDefinition> getSortDefinitions();

	Pagination ALL = new Pagination() {
		public int getStart() {
			return 0;
		}

		public int getCount() {
			return -1;
		}

		public Collection<SortDefinition> getSortDefinitions() {
			return Collections.emptyList();
		}
	};

	Pagination UNIQUE = new Pagination() {
		public int getStart() {
			return 0;
		}

		public int getCount() {
			return 1;
		}

		public Collection<SortDefinition> getSortDefinitions() {
			return Collections.emptyList();
		}
	};

}
