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

package org.greatage.domain.internal;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PropertyCriteria extends AllCriteria {
    private final String path;
    private final String property;
    private final Operator operator;
    private final Object value;

    public PropertyCriteria(final String path, final String property, final Operator operator, final Object value) {
        this.path = path;
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (path != null) {
            builder.append(path).append('.');
        }
        builder.append(property);
        switch (operator) {
            case EQUAL:
                builder.append(" = ");
                break;
            case NOT_EQUAL:
                builder.append(" <> ");
                break;
            case GREATER_THAN:
                builder.append(" > ");
                break;
            case GREATER_OR_EQUAL:
                builder.append(" >= ");
                break;
            case LESS_THAN:
                builder.append(" < ");
                break;
            case LESS_OR_EQUAL:
                builder.append(" <= ");
                break;
            case LIKE:
                builder.append(" like ");
                break;
            case IN:
                builder.append(" in ");
                break;
        }
        builder.append(value);
        return isNegative() ? "not " + builder.toString() : builder.toString();
    }

    public enum Operator {
        EQUAL,
        NOT_EQUAL,
        GREATER_THAN,
        GREATER_OR_EQUAL,
        LESS_THAN,
        LESS_OR_EQUAL,
        LIKE,
        IN
    }
}
