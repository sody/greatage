package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAECondition implements ChangeLog.Condition {
    private final List<Query.FilterPredicate> filter = new ArrayList<Query.FilterPredicate>();

    GAECondition(final Query.FilterPredicate filter) {
        this.filter.add(filter);
    }

    public ChangeLog.Condition and(final ChangeLog.Condition condition) {
        this.filter.addAll(((GAECondition) condition).filter);
        return this;
    }

    public ChangeLog.Condition or(final ChangeLog.Condition condition) {
        throw new UnsupportedOperationException("This operation is not supported in GAE DataStore");
    }

    void apply(final Query query) {
        for (Query.FilterPredicate predicate : filter) {
            query.addFilter(predicate.getPropertyName(), predicate.getOperator(), predicate.getValue());
        }
    }

    @Override
    public String toString() {
        return new DescriptionBuilder(this).append(filter).toString();
    }
}
