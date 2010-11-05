/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.SortDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represents featured implementation of {@link org.greatage.domain.Pagination} with programmatic build and lazy
 * initialization.
 *
 * @author Ivan Khalopik
 */
public class PaginationBuilder implements Pagination {
	private int start;
	private int count;

	private int from;
	private int to;
	private int page;
	private int size = -1;
	private final List<SortDefinition> sortDefinitions = new ArrayList<SortDefinition>();

	private boolean modified = true;

	public int getStart() {
		lazyBuild();
		return start;
	}

	public int getCount() {
		lazyBuild();
		return count;
	}

	public Collection<SortDefinition> getSortDefinitions() {
		lazyBuild();
		return sortDefinitions;
	}

	/**
	 * Sets start pagination position.
	 *
	 * @param start start pagination position
	 * @return this
	 */
	public PaginationBuilder start(int start) {
		this.from = start;
		return modify();
	}

	/**
	 * Sets end pagination position.
	 *
	 * @param end end pagination position
	 * @return this
	 * @throws UnsupportedOperationException if page or size parameter are specified
	 */
	public PaginationBuilder end(int end) {
		if (page > 0 || size > 0) {
			throw new UnsupportedOperationException("page or size attributes already exists");
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
	public PaginationBuilder count(int count) {
		return size(count);
	}

	/**
	 * Sets pagination size of selection.
	 *
	 * @param size pagination size of selection
	 * @return this
	 * @throws UnsupportedOperationException if end parameter are specified
	 */
	public PaginationBuilder size(int size) {
		if (to > 0) {
			throw new UnsupportedOperationException("to attribute already exists");
		}
		this.size = size;
		return modify();
	}

	/**
	 * Sets selection page.
	 *
	 * @param page selection page
	 * @return this
	 * @throws UnsupportedOperationException if end parameter are specified
	 */
	public PaginationBuilder page(int page) {
		if (to > 0) {
			throw new UnsupportedOperationException("to attribute already exists");
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
	public PaginationBuilder sort(String property) {
		return sort(property, true);
	}

	/**
	 * Sets case insensitive sort order by property, parameterized by ascending.
	 *
	 * @param property  sort property
	 * @param ascending ascending sort order
	 * @return this
	 */
	public PaginationBuilder sort(String property, boolean ascending) {
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
	public PaginationBuilder sort(String property, boolean ascending, boolean ignoreCase) {
		sortDefinitions.add(new MutableSortDefinition(property, ignoreCase, ascending));
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
		final StringBuilder sb = new StringBuilder("Pagination(");
		sb.append("start=").append(start);
		sb.append(",count=").append(count);
		sb.append(")");
		return sb.toString();
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
