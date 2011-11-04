package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.Trick;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEConditionEntry implements Trick.ConditionEntry {
	private final String propertyName;

	GAEConditionEntry(final String propertyName) {
		this.propertyName = propertyName;
	}

	public Trick.Condition greaterThan(final Object value) {
		return createCondition(Query.FilterOperator.GREATER_THAN, value);
	}

	public Trick.Condition greaterOrEqual(final Object value) {
		return createCondition(Query.FilterOperator.GREATER_THAN_OR_EQUAL, value);
	}

	public Trick.Condition lessThan(final Object value) {
		return createCondition(Query.FilterOperator.LESS_THAN, value);
	}

	public Trick.Condition lessOrEqual(final Object value) {
		return createCondition(Query.FilterOperator.LESS_THAN_OR_EQUAL, value);
	}

	public Trick.Condition equal(final Object value) {
		return createCondition(Query.FilterOperator.EQUAL, value);
	}

	public Trick.Condition notEqual(final Object value) {
		return createCondition(Query.FilterOperator.NOT_EQUAL, value);
	}

	private Trick.Condition createCondition(final Query.FilterOperator operator, final Object value) {
		return new GAECondition(new Query.FilterPredicate(propertyName, operator, value));
	}
}
