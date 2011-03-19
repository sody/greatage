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
