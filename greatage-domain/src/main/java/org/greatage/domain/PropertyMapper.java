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

import org.greatage.domain.internal.PropertyCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PropertyMapper<PK extends Serializable, E extends Entity<PK>, V>
		implements Repository.Property {
	private final String path;
	private final String property;

	public PropertyMapper(final String path, final String property) {
		this.path = path;
		this.property = property;
	}

	public String getPath() {
		return path;
	}

	public String getProperty() {
		return property;
	}

	public Repository.Criteria<PK, E> isNull() {
		return equal(null);
	}

	public Repository.Criteria<PK, E> notNull() {
		return notEqual(null);
	}

	public Repository.Criteria<PK, E> eq(final V value) {
		return equal(value);
	}

	public Repository.Criteria<PK, E> equal(final V value) {
		return createCriteria(PropertyCriteria.Operator.EQUAL, value);
	}

	public Repository.Criteria<PK, E> ne(final V value) {
		return notEqual(value);
	}

	public Repository.Criteria<PK, E> notEqual(final V value) {
		return createCriteria(PropertyCriteria.Operator.NOT_EQUAL, value);
	}

	public Repository.Criteria<PK, E> gt(final V value) {
		return greaterThan(value);
	}

	public Repository.Criteria<PK, E> greaterThan(final V value) {
		return createCriteria(PropertyCriteria.Operator.GREATER_THAN, value);
	}

	public Repository.Criteria<PK, E> ge(final V value) {
		return greaterOrEqual(value);
	}

	public Repository.Criteria<PK, E> greaterOrEqual(final V value) {
		return createCriteria(PropertyCriteria.Operator.GREATER_OR_EQUAL, value);
	}

	public Repository.Criteria<PK, E> lt(final V value) {
		return lessThan(value);
	}

	public Repository.Criteria<PK, E> lessThan(final V value) {
		return createCriteria(PropertyCriteria.Operator.LESS_THAN, value);
	}

	public Repository.Criteria<PK, E> le(final V value) {
		return lessOrEqual(value);
	}

	public Repository.Criteria<PK, E> lessOrEqual(final V value) {
		return createCriteria(PropertyCriteria.Operator.LESS_OR_EQUAL, value);
	}

	public Repository.Criteria<PK, E> like(final V value) {
		return createCriteria(PropertyCriteria.Operator.LIKE, value);
	}

	public Repository.Criteria<PK, E> in(final V... values) {
		final List<V> value = Arrays.asList(values);
		return createCriteria(PropertyCriteria.Operator.IN, value);
	}

	public Repository.Criteria<PK, E> in(final Collection<V> values) {
		final List<V> value = new ArrayList<V>(values);
		return createCriteria(PropertyCriteria.Operator.IN, value);
	}

	private Repository.Criteria<PK, E> createCriteria(final PropertyCriteria.Operator operator, final Object value) {
		return new PropertyCriteria<PK, E>(path, property, operator, value);
	}
}
