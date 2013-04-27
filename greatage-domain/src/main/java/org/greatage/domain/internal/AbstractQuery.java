package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractQuery<PK extends Serializable, E extends Entity<PK>>
        implements Query<PK, E> {

    private final Class<E> entityClass;

    private Criteria<PK, E> criteria;
    private List<Property> fetches;
    private List<Sort> sorts;

    private int start = 0;
    private int count = -1;

    private JunctionCriteria<PK, E> junction;
    private final Stack<JunctionCriteria<PK, E>> junctionStack = new Stack<JunctionCriteria<PK, E>>();

    protected AbstractQuery(final Class<E> entityClass) {
        this.entityClass = entityClass;

        criteria = junction = new JunctionCriteria<PK, E>(JunctionCriteria.Operator.AND);
    }

    public Query<PK, E> filter(final Criteria<PK, E> criteria) {
        junction.add(criteria);
        return this;
    }

    public Query<PK, E> filter(final String filter, final Object value) {
        //TODO: implement this
        throw new UnsupportedOperationException();
    }

    public Query<PK, E> and() {
        if (junction.getOperator() != JunctionCriteria.Operator.AND) {
            final JunctionCriteria<PK, E> newJunction = new JunctionCriteria<PK, E>(JunctionCriteria.Operator.AND);
            junction.add(newJunction);
            junction = newJunction;
        }
        junctionStack.push(junction);
        return this;
    }

    public Query<PK, E> or() {
        if (junction.getOperator() != JunctionCriteria.Operator.OR) {
            final JunctionCriteria<PK, E> newJunction = new JunctionCriteria<PK, E>(JunctionCriteria.Operator.OR);
            junction.add(newJunction);
            junction = newJunction;
        }
        junctionStack.push(junction);
        return this;
    }

    public Query<PK, E> end() {
        junction = junctionStack.pop();
        return this;
    }

    public Query<PK, E> fetch(final Property property) {
        if (fetches == null) {
            fetches = new ArrayList<Property>();
        }
        fetches.add(property);
        return this;
    }

    public Query<PK, E> fetch(final Property property, final boolean fetch) {
        //TODO: implement this logic
        return fetch(property);
    }

    public Query<PK, E> sort(final Property property) {
        return sort(property, true);
    }

    public Query<PK, E> sort(final Property property, final boolean ascending) {
        return sort(property, ascending, false);
    }

    public Query<PK, E> sort(final Property property, final boolean ascending, final boolean ignoreCase) {
        if (sorts == null) {
            sorts = new ArrayList<Sort>();
        }
        sorts.add(new Sort(property, ascending, ignoreCase));
        return this;
    }

    public Query<PK, E> skip(final int count) {
        this.start = count;
        return this;
    }

    public Query<PK, E> limit(final int count) {
        this.count = count;
        return this;
    }

    public Query<PK, E> paginate(final int start, final int count) {
        this.start = start;
        this.count = count;
        return this;
    }

    public E first() {
        //TODO: implement this
        throw new UnsupportedOperationException();
    }

    public Iterable<E> iterate() {
        //TODO: implement this
        throw new UnsupportedOperationException();
    }

    public Iterable<PK> iterateKeys() {
        //TODO: implement this
        throw new UnsupportedOperationException();
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public Criteria<PK, E> getCriteria() {
        return criteria;
    }

    public List<Property> getFetches() {
        return fetches;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    class Sort {
        private final Property property;
        private final boolean ascending;
        private final boolean ignoreCase;

        Sort(final Property property, final boolean ascending, final boolean ignoreCase) {
            this.property = property;
            this.ascending = ascending;
            this.ignoreCase = ignoreCase;
        }

        public Property getProperty() {
            return property;
        }

        public boolean isAscending() {
            return ascending;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }
    }
}
