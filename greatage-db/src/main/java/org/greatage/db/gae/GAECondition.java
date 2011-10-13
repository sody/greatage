package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAECondition<T extends ChangeBuilder> implements ConditionBuilder<T> {
	private final T change;

	GAECondition(final T change) {
		this.change = change;
	}

	public ConditionEntryBuilder<T> and(final String propertyName) {
		return new GAEConditionEntry(propertyName);
	}

	public T end() {
		return change;
	}

	class GAEConditionEntry implements ConditionEntryBuilder<T> {
		private final String propertyName;

		GAEConditionEntry(final String propertyName) {
			this.propertyName = propertyName;
		}

		public ConditionBuilder<T> greaterThan(final Object value) {
			return addCondition(Query.FilterOperator.GREATER_THAN, value);
		}

		public ConditionBuilder<T> greaterOrEqual(final Object value) {
			return addCondition(Query.FilterOperator.GREATER_THAN_OR_EQUAL, value);
		}

		public ConditionBuilder<T> lessThan(final Object value) {
			return addCondition(Query.FilterOperator.LESS_THAN, value);
		}

		public ConditionBuilder<T> lessOrEqual(final Object value) {
			return addCondition(Query.FilterOperator.LESS_THAN_OR_EQUAL, value);
		}

		public ConditionBuilder<T> equal(final Object value) {
			return addCondition(Query.FilterOperator.EQUAL, value);
		}

		public ConditionBuilder<T> notEqual(final Object value) {
			return addCondition(Query.FilterOperator.NOT_EQUAL, value);
		}

		GAECondition<T> addCondition(final Query.FilterOperator operator, final Object value) {
			((GAEConditionalChange) change).addCondition(new Query.FilterPredicate(propertyName, operator, value));
			return GAECondition.this;
		}
	}
}
