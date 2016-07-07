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

import org.greatage.domain.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JunctionCriteria implements Query.Criteria {
    private final List<Query.Criteria> children;
    private final Operator operator;

    public JunctionCriteria(final Operator operator) {
        this(operator, new ArrayList<Query.Criteria>());
    }

    public JunctionCriteria(final Operator operator, final List<Query.Criteria> children) {
        this.operator = operator;
        this.children = children;
    }

    public Query.Criteria add(final Query.Criteria criteria) {
        children.add(criteria);
        return this;
    }

    public Query.Criteria add(final List<Query.Criteria> criteria) {
        children.addAll(criteria);
        return this;
    }

    public List<Query.Criteria> getChildren() {
        return children;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("(");
        for (Query.Criteria child : children) {
            if (builder.length() > 1) {
                builder.append(operator == Operator.AND ? " and " : " or ");
            }
            builder.append(child);
        }
        builder.append(")");

        return builder.toString();
    }

    public enum Operator {
        AND,
        OR
    }
}
