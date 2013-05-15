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

package org.greatage.domain.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.Query;
import org.greatage.domain.internal.*;
import org.greatage.domain.internal.NameAllocator;
import org.hibernate.criterion.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateQueryVisitor<PK extends Serializable, E extends Entity<PK>>
        extends AbstractQueryVisitor<PK, E> {

    private final Map<String, org.hibernate.Criteria> children = new HashMap<String, org.hibernate.Criteria>();
    private final NameAllocator names = new NameAllocator();

    private final org.hibernate.Criteria root;

    private Junction junction;
    private boolean negative;
    private String path;
    private String property;

    HibernateQueryVisitor(final org.hibernate.Criteria root) {
        this.root = root;
    }

    @Override
    protected void visitJunction(final JunctionCriteria criteria) {
        // backup previous junction
        final Junction parent = junction;
        // backup previous negative flag
        final boolean parentNegative = negative;
        // create new junction
        final Junction current = criteria.getOperator() == JunctionCriteria.Operator.AND ?
                Restrictions.conjunction() :
                Restrictions.disjunction();

        // replace current junction
        junction = current;
        // reset negative flag
        negative = false;
        // process child criteria
        for (Query.Criteria child : criteria.getChildren()) {
            visitCriteria(child);
        }
        // restore previous junction
        junction = parent;
        // restore previous negative flag
        negative = parentNegative;

        // add junction to the criteria
        addCriterion(current);
    }

    @Override
    protected void visitNegative(final NegativeCriteria criteria) {
        // backup previous negative flag
        final boolean parentNegative = negative;
        // setup negative flag
        negative = true;
        // process child criteria
        visitCriteria(criteria.getCriteria());
        // restore previous negative flag
        negative = parentNegative;
    }

    @Override
    protected void visitChild(final ChildCriteria criteria) {
        // backup previous path
        final String parentPath = path;
        final String parentProperty = property;

        // replace current path
        path = resolvePath(criteria.getPath());
        property = resolveProperty(criteria.getPath(), criteria.getProperty());
        // visit child criteria
        visitCriteria(criteria.getCriteria());
        // restore previous path
        path = parentPath;
        property = parentProperty;
    }

    @Override
    protected void visitAll(final AllCriteria criteria) {
        addCriterion(Restrictions.sqlRestriction("1=1"));
    }

    @Override
    protected void visitEqual(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        if (criteria.getValue() == null) {
            addCriterion(property.isNull());
        } else {
            addCriterion(property.eq(criteria.getValue()));
        }
    }

    @Override
    protected void visitNotEqual(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        if (criteria.getValue() == null) {
            addCriterion(property.isNotNull());
        } else {
            addCriterion(property.ne(criteria.getValue()));
        }
    }

    @Override
    protected void visitGreaterThan(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        addCriterion(property.gt(criteria.getValue()));
    }

    @Override
    protected void visitGreaterOrEqual(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        addCriterion(property.ge(criteria.getValue()));
    }

    @Override
    protected void visitLessThan(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        addCriterion(property.lt(criteria.getValue()));
    }

    @Override
    protected void visitLessOrEqual(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        addCriterion(property.le(criteria.getValue()));
    }

    @Override
    protected void visitIn(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        final List<?> value = (List<?>) criteria.getValue();
        if (value == null || value.isEmpty()) {
            addCriterion(Restrictions.sqlRestriction("1=2"));
        } else {
            addCriterion(property.in(value));
        }
    }

    @Override
    protected void visitNotIn(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        final List<?> value = (List<?>) criteria.getValue();
        if (value == null || value.isEmpty()) {
            addCriterion(Restrictions.sqlRestriction("1=1"));
        } else {
            addCriterion(Restrictions.not(property.in(value)));
        }
    }

    @Override
    protected void visitLike(final PropertyCriteria criteria) {
        final Property property = getProperty(criteria);

        addCriterion(property.like(criteria.getValue()));
    }

    @Override
    protected void visitFetch(final Query.Property property, boolean fetch) {
        //todo: implement this
    }

    @Override
    protected void visitSort(final Query.Property property, final boolean ascending, final boolean ignoreCase) {
        final Order order = ascending ?
                Order.asc(property.getProperty()) :
                Order.desc(property.getProperty());

        if (ignoreCase) {
            order.ignoreCase();
        }
        getCriteria(property.getPath()).addOrder(order);
    }

    @Override
    protected void visitPagination(final int start, final int count) {
        if (start > 0) {
            root.setFirstResult(start);
        }
        if (count >= 0) {
            root.setMaxResults(count);
        }
    }

    private void addCriterion(final Criterion criterion) {
        if (junction != null) {
            junction.add(negative ? Restrictions.not(criterion) : criterion);
        } else {
            root.add(negative ? Restrictions.not(criterion) : criterion);
        }
    }

    private Property getProperty(final PropertyCriteria criteria) {
        final String path = resolvePath(criteria.getPath());
        final String property = resolveProperty(criteria.getPath(), criteria.getProperty());
        final String alias = getCriteria(path).getAlias();
        return Property.forName(alias + "." + property);
    }

    private String resolvePath(final String path) {
        return path != null ? join(join(this.path, this.property), path) : this.path;
    }

    private String resolveProperty(final String path, final String property) {
        return path != null ? property : join(this.property, property);
    }

    private String join(final String path, final String property) {
        return path != null ?
                property != null ?
                        path + "." + property :
                        path :
                property;
    }

    private org.hibernate.Criteria getCriteria(final String path) {
        if (path == null) {
            return root;
        }
        if (!children.containsKey(path)) {
            children.put(path, createCriteria(path));
        }
        return children.get(path);
    }

    private org.hibernate.Criteria createCriteria(final String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Empty path");
        }
        final int i = path.lastIndexOf('.');
        final String property = i > 0 ? path.substring(i + 1) : path;
        return root.createCriteria(path, names.allocate(property));
    }
}
