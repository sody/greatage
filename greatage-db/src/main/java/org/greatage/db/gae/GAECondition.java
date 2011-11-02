package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAECondition<T> implements ChangeSetBuilder.ConditionBuilder<T> {
	private final T parent;

	GAECondition(final T parent) {
		this.parent = parent;
	}

	public ChangeSetBuilder.ConditionEntryBuilder<T> and(final String propertyName) {
		return new GAEConditionEntry(propertyName);
	}

	public T end() {
		return parent;
	}

	class GAEConditionEntry implements ChangeSetBuilder.ConditionEntryBuilder<T> {
		private final String propertyName;

		GAEConditionEntry(final String propertyName) {
			this.propertyName = propertyName;
		}

		public ChangeSetBuilder.ConditionBuilder<T> greaterThan(final Object value) {
			return addCondition(Query.FilterOperator.GREATER_THAN, value);
		}

		public ChangeSetBuilder.ConditionBuilder<T> greaterOrEqual(final Object value) {
			return addCondition(Query.FilterOperator.GREATER_THAN_OR_EQUAL, value);
		}

		public ChangeSetBuilder.ConditionBuilder<T> lessThan(final Object value) {
			return addCondition(Query.FilterOperator.LESS_THAN, value);
		}

		public ChangeSetBuilder.ConditionBuilder<T> lessOrEqual(final Object value) {
			return addCondition(Query.FilterOperator.LESS_THAN_OR_EQUAL, value);
		}

		public ChangeSetBuilder.ConditionBuilder<T> equal(final Object value) {
			return addCondition(Query.FilterOperator.EQUAL, value);
		}

		public ChangeSetBuilder.ConditionBuilder<T> notEqual(final Object value) {
			return addCondition(Query.FilterOperator.NOT_EQUAL, value);
		}

		GAECondition<T> addCondition(final Query.FilterOperator operator, final Object value) {
			((GAEConditional) parent).addCondition(new Query.FilterPredicate(propertyName, operator, value));
			return GAECondition.this;
		}
	}
}
