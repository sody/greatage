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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PropertyCriteriaBuilder<PK extends Serializable, E extends Entity<PK>, V> {
	private final String path;
	private final String property;

	public PropertyCriteriaBuilder(final String path, final String property) {
		this.property = property;
		this.path = path;
	}

	public PropertyCriteria<PK, E> isNull() {
		return equal(null);
	}

	public PropertyCriteria<PK, E> notNull() {
		return notEqual(null);
	}

	public PropertyCriteria<PK, E> eq(final V value) {
		return equal(value);
	}

	public PropertyCriteria<PK, E> equal(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.EQUAL, value);
	}

	public PropertyCriteria<PK, E> ne(final V value) {
		return notEqual(value);
	}

	public PropertyCriteria<PK, E> notEqual(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.NOT_EQUAL, value);
	}

	public PropertyCriteria<PK, E> gt(final V value) {
		return greaterThan(value);
	}

	public PropertyCriteria<PK, E> greaterThan(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.GREATER_THAN, value);
	}

	public PropertyCriteria<PK, E> ge(final V value) {
		return greaterOrEqual(value);
	}

	public PropertyCriteria<PK, E> greaterOrEqual(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.GREATER_OR_EQUAL, value);
	}

	public PropertyCriteria<PK, E> lt(final V value) {
		return lessThan(value);
	}

	public PropertyCriteria<PK, E> lessThan(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.LESS_THAN, value);
	}

	public PropertyCriteria<PK, E> le(final V value) {
		return lessOrEqual(value);
	}

	public PropertyCriteria<PK, E> lessOrEqual(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.LESS_OR_EQUAL, value);
	}

	public PropertyCriteria<PK, E> like(final V value) {
		return new PropertyCriteria<PK, E>(path, property, PropertyCriteria.Operator.LIKE, value);
	}

	public SortCriteria<PK, E> asc() {
		return sort(true);
	}

	public SortCriteria<PK, E> desc() {
		return sort(false);
	}

	public SortCriteria<PK, E> sort(final boolean ascending) {
		return sort(ascending, true);
	}

	public SortCriteria<PK, E> sort(final boolean ascending, final boolean ignoreCase) {
		return new SortCriteria<PK, E>(path, property, ascending, ignoreCase);
	}
}
