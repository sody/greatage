package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.Trick;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAECondition implements Trick.Condition {
	private final List<Query.FilterPredicate> filter = new ArrayList<Query.FilterPredicate>();

	GAECondition(final Query.FilterPredicate filter) {
		this.filter.add(filter);
	}

	public Trick.Condition and(final Trick.Condition condition) {
		final GAECondition gaeCondition = (GAECondition) condition;
		this.filter.addAll(gaeCondition.getFilter());
		return this;
	}

	public Trick.Condition or(final Trick.Condition condition) {
		throw new UnsupportedOperationException("This operation is not supported in GAE DataStore");
	}

	public List<Query.FilterPredicate> getFilter() {
		return filter;
	}
}
