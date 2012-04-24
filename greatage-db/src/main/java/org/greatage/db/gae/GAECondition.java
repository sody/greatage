package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAECondition implements ChangeSet.Condition {
	private final List<Query.FilterPredicate> filter = new ArrayList<Query.FilterPredicate>();

	GAECondition(final Query.FilterPredicate filter) {
		this.filter.add(filter);
	}

	public ChangeSet.Condition and(final ChangeSet.Condition condition) {
		final GAECondition gaeCondition = (GAECondition) condition;
		this.filter.addAll(gaeCondition.getFilter());
		return this;
	}

	public ChangeSet.Condition or(final ChangeSet.Condition condition) {
		throw new UnsupportedOperationException("This operation is not supported in GAE DataStore");
	}

	public List<Query.FilterPredicate> getFilter() {
		return filter;
	}
}
