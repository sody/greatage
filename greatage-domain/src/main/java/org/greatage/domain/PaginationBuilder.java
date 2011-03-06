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

import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;

import java.util.Collection;
import java.util.List;

/**
 * This class represents featured implementation of {@link org.greatage.domain.Pagination} with programmatic build and
 * lazy initialization.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PaginationBuilder implements Pagination {
	private int start;
	private int count;

	private int from;
	private int to;
	private int page;
	private int size = -1;
	private final List<SortConstraint> sortDefinitions = CollectionUtils.newList();

	private boolean modified = true;

	public int getStart() {
		lazyBuild();
		return start;
	}

	public int getCount() {
		lazyBuild();
		return count;
	}

	public Collection<SortConstraint> getSortConstraints() {
		lazyBuild();
		return sortDefinitions;
	}

	/**
	 * Sets start pagination position.
	 *
	 * @param start start pagination position
	 * @return this
	 */
	public PaginationBuilder start(final int start) {
		this.from = start;
		return modify();
	}

	/**
	 * Sets end pagination position.
	 *
	 * @param end end pagination position
	 * @return this
	 * @throws IllegalStateException if page or size parameter are specified
	 */
	public PaginationBuilder end(final int end) {
		if (page > 0 || size > 0) {
			throw new IllegalStateException("page or size attributes already exists");
		}
		this.to = end;
		return modify();
	}

	/**
	 * Sets pagination count of rows.
	 *
	 * @param count pagination count of rows
	 * @return this
	 * @see #size(int)
	 */
	public PaginationBuilder count(final int count) {
		return size(count);
	}

	/**
	 * Sets pagination size of selection.
	 *
	 * @param size pagination size of selection
	 * @return this
	 * @throws IllegalStateException if end parameter are specified
	 */
	public PaginationBuilder size(final int size) {
		if (to > 0) {
			throw new IllegalStateException("to attribute already exists");
		}
		this.size = size;
		return modify();
	}

	/**
	 * Sets selection page.
	 *
	 * @param page selection page
	 * @return this
	 * @throws IllegalStateException if end parameter are specified
	 */
	public PaginationBuilder page(final int page) {
		if (to > 0) {
			throw new IllegalStateException("to attribute already exists");
		}
		this.page = page;
		return modify();
	}

	/**
	 * Sets case sensitive ascending sort order by property.
	 *
	 * @param property sort property
	 * @return this
	 */
	public PaginationBuilder sort(final String property) {
		return sort(property, true);
	}

	/**
	 * Sets case insensitive sort order by property, parameterized by ascending.
	 *
	 * @param property  sort property
	 * @param ascending ascending sort order
	 * @return this
	 */
	public PaginationBuilder sort(final String property, final boolean ascending) {
		return sort(property, ascending, true);
	}

	/**
	 * Sets sort order by property, parameterized by ascending and ignoreCase.
	 *
	 * @param property   sort property
	 * @param ascending  ascending sort order
	 * @param ignoreCase ignore case
	 * @return this
	 */
	public PaginationBuilder sort(final String property, final boolean ascending, final boolean ignoreCase) {
		sortDefinitions.add(new SortConstraintImpl(property, ascending, ignoreCase));
		return modify();
	}

	/**
	 * Clears sort orders.
	 *
	 * @return this
	 */
	public PaginationBuilder unsorted() {
		sortDefinitions.clear();
		return modify();
	}

	/**
	 * Sets modified flag, that determines if pagination parameters were modified.
	 *
	 * @return this
	 */
	private PaginationBuilder modify() {
		this.modified = true;
		return this;
	}

	/**
	 * Initializes general parameters (start and count) if parameters were modified.
	 */
	private void lazyBuild() {
		if (modified) {
			if (page > 0 && size >= 0) {
				start = from + (page - 1) * size;
				count = size;
			} else if (to > 0 && to >= from) {
				start = from;
				count = to - from;
			} else {
				start = from;
				count = size >= 0 ? size : -1;
			}
			modified = false;
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(Pagination.class);
		builder.append("start", start);
		builder.append("count", count);
		return builder.toString();
	}

	/**
	 * Creates default pagination builder.
	 *
	 * @return default pagination builder
	 */
	public static PaginationBuilder create() {
		return new PaginationBuilder();
	}
}
