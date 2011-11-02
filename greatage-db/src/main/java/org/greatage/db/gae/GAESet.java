package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSetBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAESet<T extends ChangeSetBuilder.ChangeBuilder> implements ChangeSetBuilder.SetBuilder<T>, GAEConditional {
	private final T change;
	private final String into;

	private final Query query;

	GAESet(final T change, final String into, final String from) {
		this.change = change;
		this.into = into;
		query = new Query(from);
	}

	public ChangeSetBuilder.ConditionEntryBuilder<ChangeSetBuilder.SetBuilder<T>> where(final String property) {
		return new GAECondition<ChangeSetBuilder.SetBuilder<T>>(this).and(property);
	}

	public void addCondition(final Query.FilterPredicate condition) {
		query.addFilter(condition.getPropertyName(), condition.getOperator(), condition.getValue());
	}

	public T end() {
		((GAESettable) change).addSelect(into, query);
		return change;
	}
}
