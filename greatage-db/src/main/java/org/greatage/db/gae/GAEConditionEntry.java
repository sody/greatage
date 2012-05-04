package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;

import java.util.Arrays;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEConditionEntry implements ChangeLog.ConditionEntry {
    private final String propertyName;

    GAEConditionEntry(final String propertyName) {
        this.propertyName = propertyName;
    }

    public ChangeLog.Condition gt(final Object value) {
        return createCondition(Query.FilterOperator.GREATER_THAN, value);
    }

    public ChangeLog.Condition ge(final Object value) {
        return createCondition(Query.FilterOperator.GREATER_THAN_OR_EQUAL, value);
    }

    public ChangeLog.Condition lt(final Object value) {
        return createCondition(Query.FilterOperator.LESS_THAN, value);
    }

    public ChangeLog.Condition le(final Object value) {
        return createCondition(Query.FilterOperator.LESS_THAN_OR_EQUAL, value);
    }

    public ChangeLog.Condition eq(final Object value) {
        return createCondition(Query.FilterOperator.EQUAL, value);
    }

    public ChangeLog.Condition ne(final Object value) {
        return createCondition(Query.FilterOperator.NOT_EQUAL, value);
    }

    public ChangeLog.Condition in(final Object... values) {
        return createCondition(Query.FilterOperator.IN, Arrays.asList(values));
    }

    private ChangeLog.Condition createCondition(final Query.FilterOperator operator, final Object value) {
        return new GAECondition(new Query.FilterPredicate(propertyName, operator, value));
    }
}
