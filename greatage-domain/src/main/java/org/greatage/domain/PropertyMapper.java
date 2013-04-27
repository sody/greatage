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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PropertyMapper<V> implements Query.Property {
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

    public Query.Criteria isNull() {
        return equal(null);
    }

    public Query.Criteria notNull() {
        return notEqual(null);
    }

    public Query.Criteria eq(final V value) {
        return equal(value);
    }

    public Query.Criteria equal(final V value) {
        return createCriteria(PropertyCriteria.Operator.EQUAL, value);
    }

    public Query.Criteria ne(final V value) {
        return notEqual(value);
    }

    public Query.Criteria notEqual(final V value) {
        return createCriteria(PropertyCriteria.Operator.NOT_EQUAL, value);
    }

    public Query.Criteria gt(final V value) {
        return greaterThan(value);
    }

    public Query.Criteria greaterThan(final V value) {
        return createCriteria(PropertyCriteria.Operator.GREATER_THAN, value);
    }

    public Query.Criteria ge(final V value) {
        return greaterOrEqual(value);
    }

    public Query.Criteria greaterOrEqual(final V value) {
        return createCriteria(PropertyCriteria.Operator.GREATER_OR_EQUAL, value);
    }

    public Query.Criteria lt(final V value) {
        return lessThan(value);
    }

    public Query.Criteria lessThan(final V value) {
        return createCriteria(PropertyCriteria.Operator.LESS_THAN, value);
    }

    public Query.Criteria le(final V value) {
        return lessOrEqual(value);
    }

    public Query.Criteria lessOrEqual(final V value) {
        return createCriteria(PropertyCriteria.Operator.LESS_OR_EQUAL, value);
    }

    public Query.Criteria like(final V value) {
        return createCriteria(PropertyCriteria.Operator.LIKE, value);
    }

    public Query.Criteria in(final V... values) {
        final List<V> value = Arrays.asList(values);
        return createCriteria(PropertyCriteria.Operator.IN, value);
    }

    public Query.Criteria in(final Collection<V> values) {
        final List<V> value = new ArrayList<V>(values);
        return createCriteria(PropertyCriteria.Operator.IN, value);
    }

    private Query.Criteria createCriteria(final PropertyCriteria.Operator operator, final Object value) {
        return new PropertyCriteria(path, property, operator, value);
    }
}
