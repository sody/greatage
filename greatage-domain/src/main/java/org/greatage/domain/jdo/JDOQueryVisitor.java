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

package org.greatage.domain.jdo;

import org.greatage.domain.Entity;
import org.greatage.domain.internal.*;
import org.greatage.domain.internal.NameAllocator;

import javax.jdo.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDOQueryVisitor<PK extends Serializable, E extends Entity<PK>>
        extends AbstractQueryVisitor<PK, E> {
    private final Map<String, Object> parameters = new HashMap<String, Object>();
    private final NameAllocator names = new NameAllocator();

    private final Query query;
    private List<String> junction = new ArrayList<String>();
    private int level;
    private boolean negative;
    private String path;
    private String property;

    public JDOQueryVisitor(final Query query) {
        this.query = query;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public void visitCriteria(final org.greatage.domain.Query.Criteria criteria) {
        level++;
        super.visitCriteria(criteria);
        level--;
        // google-app-engine fix for empty filter
        if (level == 0 && !junction.isEmpty()) {
            query.setFilter(getJunction(junction, " && "));
        }
    }

    @Override
    protected void visitJunction(final JunctionCriteria criteria) {
        final List<String> parent = this.junction;
        junction = new ArrayList<String>();

        for (org.greatage.domain.Query.Criteria child : criteria.getChildren()) {
            visitCriteria(child);
        }

        final List<String> temp = junction;
        junction = parent;

        final String function = criteria.getOperator() == JunctionCriteria.Operator.AND ? " && " : " || ";
        final String filter = getJunction(temp, function);
        addCriterion(filter);
    }

    @Override
    protected void visitNegative(final NegativeCriteria criteria) {
        //TODO: implement it
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visitChild(final ChildCriteria criteria) {
        final String parentPath = path;
        final String parentProperty = property;

        path = criteria.getPath();
        property = criteria.getProperty();
        visitCriteria(criteria.getCriteria());
        path = parentPath;
        property = parentProperty;
    }

    @Override
    protected void visitAll(final AllCriteria criteria) {
        //TODO: implement it
    }

    @Override
    protected void visitEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String parameterName = parameterName(criteria);
        final String criterion = propertyName + " == :" + parameterName;

        addParameter(parameterName, criteria.getValue());
        addCriterion(criterion);
    }

    @Override
    protected void visitNotEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String parameterName = parameterName(criteria);
        final String criterion = propertyName + " != :" + parameterName;

        addParameter(parameterName, criteria.getValue());
        addCriterion(criterion);
    }

    @Override
    protected void visitGreaterThan(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);

        if (criteria.getValue() != null) {
            final String parameterName = parameterName(criteria);
            final String criterion = propertyName + " > :" + parameterName;

            addParameter(parameterName, criteria.getValue());
            addCriterion(criterion);
        } else {
            final String criterion = "false";

            addCriterion(criterion);
        }
    }

    @Override
    protected void visitGreaterOrEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);

        if (criteria.getValue() != null) {
            final String parameterName = parameterName(criteria);
            final String criterion = propertyName + " >= :" + parameterName;

            addParameter(parameterName, criteria.getValue());
            addCriterion(criterion);
        } else {
            final String criterion = "false";

            addCriterion(criterion);
        }
    }

    @Override
    protected void visitLessThan(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);

        if (criteria.getValue() != null) {
            final String parameterName = parameterName(criteria);
            final String criterion = propertyName + " < :" + parameterName;

            addParameter(parameterName, criteria.getValue());
            addCriterion(criterion);
        } else {
            // there are no values less than null
            final String criterion = "false";

            addCriterion(criterion);
        }
    }

    @Override
    protected void visitLessOrEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);

        if (criteria.getValue() != null) {
            final String parameterName = parameterName(criteria);
            final String criterion = propertyName + " <= :" + parameterName;

            addParameter(parameterName, criteria.getValue());
            addCriterion(criterion);
        } else {
            final String criterion = "false";

            addCriterion(criterion);
        }
    }

    @Override
    protected void visitIn(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final List<?> value = (List<?>) criteria.getValue();

        if (value == null || value.isEmpty()) {
            addCriterion("false");
        } else if (value.contains(null)) {
            final String parameterName = parameterName(criteria);
            final String criterion = ":" + parameterName + ".contains(" + propertyName + ")";

            final List<Object> recalculated = new ArrayList<Object>();
            for (Object val : value) {
                if (val != null) {
                    recalculated.add(val);
                }
            }
            addParameter(parameterName, recalculated);
            addCriterion(criterion);
        } else {
            final String parameterName = parameterName(criteria);
            final String criterion = ":" + parameterName + ".contains(" + propertyName + ")";

            addParameter(parameterName, value);
            addCriterion(criterion);
        }
    }

    @Override
    protected void visitNotIn(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitLike(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitFetch(final org.greatage.domain.Query.Property property, boolean fetch) {
        //todo: implement this
    }

    @Override
    protected void visitSort(final org.greatage.domain.Query.Property property, final boolean ascending, final boolean ignoreCase) {
        //todo: implement this
    }

    @Override
    protected void visitPagination(final int start, final int count) {
        if (count >= 0) {
            final int from = start > 0 ? start : 0;
            final int to = from + count;
            query.setRange(from, to);
        }
    }

    private void addParameter(final String parameterName, final Object value) {
        parameters.put(parameterName, value);
    }

    private void addCriterion(final String criterion) {
        junction.add(negative ? "!(" + criterion + ")" : criterion);
    }

    private String getJunction(final List<String> children, final String function) {
        if (children.isEmpty()) {
            return "";
        }
        if (children.size() == 1) {
            return children.get(0);
        }
        final StringBuilder criterion = new StringBuilder();
        for (String child : children) {
            if (criterion.length() > 0) {
                criterion.append(function);
            }
            criterion.append(child);
        }
        return criterion.toString();
    }

    private String propertyName(final PropertyCriteria criteria) {
        final String path = toPath(this.path, criteria.getPath());
        final String property = toPath(this.property, criteria.getProperty());
        return toPath(path, property);
    }

    private String parameterName(final PropertyCriteria criteria) {
        return names.allocate(criteria.getProperty());
    }

    private String toPath(final String path, final String property) {
        return path != null ?
                property != null ?
                        path + "." + property :
                        path :
                property;
    }
}
