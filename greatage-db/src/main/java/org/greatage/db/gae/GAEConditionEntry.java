package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSet;

import java.util.Arrays;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEConditionEntry implements ChangeSet.ConditionEntry {
	private final String propertyName;

	GAEConditionEntry(final String propertyName) {
		this.propertyName = propertyName;
	}

	public ChangeSet.Condition greaterThan(final Object value) {
		return createCondition(Query.FilterOperator.GREATER_THAN, value);
	}

	public ChangeSet.Condition greaterOrEqual(final Object value) {
		return createCondition(Query.FilterOperator.GREATER_THAN_OR_EQUAL, value);
	}

	public ChangeSet.Condition lessThan(final Object value) {
		return createCondition(Query.FilterOperator.LESS_THAN, value);
	}

	public ChangeSet.Condition lessOrEqual(final Object value) {
		return createCondition(Query.FilterOperator.LESS_THAN_OR_EQUAL, value);
	}

	public ChangeSet.Condition equal(final Object value) {
		return createCondition(Query.FilterOperator.EQUAL, value);
	}

	public ChangeSet.Condition notEqual(final Object value) {
		return createCondition(Query.FilterOperator.NOT_EQUAL, value);
	}

	public ChangeSet.Condition in(final Object... values) {
		return createCondition(Query.FilterOperator.IN, Arrays.asList(values));
	}

	private ChangeSet.Condition createCondition(final Query.FilterOperator operator, final Object value) {
		return new GAECondition(new Query.FilterPredicate(propertyName, operator, value));
	}
}
