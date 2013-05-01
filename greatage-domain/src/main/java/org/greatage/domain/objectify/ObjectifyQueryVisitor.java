package org.greatage.domain.objectify;

import com.googlecode.objectify.Query;
import org.greatage.domain.Entity;
import org.greatage.domain.internal.AbstractQueryVisitor;
import org.greatage.domain.internal.ChildCriteria;
import org.greatage.domain.internal.JunctionCriteria;
import org.greatage.domain.internal.PropertyCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyQueryVisitor<PK extends Serializable, E extends Entity<PK>> extends AbstractQueryVisitor<PK, E> {
    private final Query<? extends E> query;

    private String property;

    public ObjectifyQueryVisitor(final Query<? extends E> query) {
        this.query = query;
    }

    @Override
    protected void visitJunction(final JunctionCriteria criteria) {
        if (criteria.getOperator() == JunctionCriteria.Operator.OR) {
            throw new UnsupportedOperationException("OR operation is not supported by appengine queries");
        }

        for (org.greatage.domain.Query.Criteria child : criteria.getChildren()) {
            visitCriteria(child);
        }
    }

    @Override
    protected void visitChild(final ChildCriteria criteria) {
        if (criteria.getPath() != null) {
            throw new UnsupportedOperationException("External criteria is not supported by appengine queries");
        }

        final String parentProperty = property;
        property = criteria.getProperty();
        visitCriteria(criteria.getCriteria());
        property = parentProperty;
    }

    @Override
    protected void visitProperty(final PropertyCriteria criteria) {
        if (criteria.getPath() != null) {
            throw new UnsupportedOperationException("External criteria is not supported by appengine queries");
        }

        super.visitProperty(criteria);
    }

    @Override
    protected void visitEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " =";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always false
                query.filter("1 =", 0);
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitNotEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " !=";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always true
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitGreaterThan(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " >";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always true
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitGreaterOrEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " >=";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always true
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitLessThan(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " <";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always false
                query.filter("1 =", 0);
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitLessOrEqual(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " <=";

        if (criteria.getValue() == null) {
            try {
                query.filter(criterion, criteria.getValue());
            } catch (NullPointerException e) {
                //it is always false
                query.filter("1 =", 0);
            }
        } else {
            query.filter(criterion, criteria.getValue());
        }
    }

    @Override
    protected void visitIn(final PropertyCriteria criteria) {
        final String propertyName = propertyName(criteria);
        final String criterion = propertyName + " in";
        final List<?> values = (List<?>) criteria.getValue();

        if (values == null || values.isEmpty()) {
            //it is always false
            query.filter("1 =", 0);
        } else if (values.contains(null)) {
            try {
                query.filter(criterion, values);
            } catch (NullPointerException e) {
                if (values.size() == 1) {
                    //it is always false
                    query.filter("1 =", 0);
                } else {
                    final List<Object> recalculated = new ArrayList<Object>();
                    for (Object value : values) {
                        if (value != null) {
                            recalculated.add(value);
                        }
                    }

                    query.filter(criterion, recalculated);
                }
            }
        } else {
            query.filter(criterion, values);
        }
    }

    @Override
    protected void visitLike(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitFetch(final org.greatage.domain.Query.Property fetch) {
        //todo: implement this
    }

    @Override
    protected void visitSort(final org.greatage.domain.Query.Property property, final boolean ascending, final boolean ignoreCase) {
        final StringBuilder condition = new StringBuilder();
        if (!ascending) {
            condition.append("-");
        }
        if (property.getPath() != null) {
            condition.append(property.getPath()).append('.');
        }
        condition.append(property.getProperty());
        query.order(condition.toString());
    }

    @Override
    protected void visitPagination(final int start, final int count) {
        if (start > 0) {
            query.offset(start);
        }
        if (count >= 0) {
            query.limit(count);
        }
    }

    private String propertyName(final PropertyCriteria criteria) {
        return toPath(this.property, criteria.getProperty());
    }

    private String toPath(final String path, final String property) {
        return path != null ?
                property != null ?
                        path + "." + property :
                        path :
                property;
    }
}
