/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * This interface  represents pagination info for selections from repository and services.
 *
 * @author Ivan Khalopik
 * @see org.greatage.domain.PaginationBuilder
 * @see EntityService
 * @see EntityRepository
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
	Collection<SortConstraint> getSortConstraints();

	Pagination ALL = new Pagination() {
		public int getStart() {
			return 0;
		}

		public int getCount() {
			return -1;
		}

		public Collection<SortConstraint> getSortConstraints() {
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

		public Collection<SortConstraint> getSortConstraints() {
			return Collections.emptyList();
		}
	};

}
