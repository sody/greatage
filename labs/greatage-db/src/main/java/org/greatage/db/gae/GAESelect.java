package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.Trick;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAESelect implements Trick.Select {
	private final Query query;
	private boolean unique;

	GAESelect(final String entityName) {
		query = new Query(entityName);
	}

	public Trick.Select unique() {
		unique = true;
		return this;
	}

	public Trick.Select where(final Trick.Condition condition) {
		final GAECondition gaeCondition = (GAECondition) condition;
		for (Query.FilterPredicate predicate : gaeCondition.getFilter()) {
			query.addFilter(predicate.getPropertyName(), predicate.getOperator(), predicate.getValue());
		}
		return this;
	}

	public Query getQuery() {
		return query;
	}

	public boolean isUnique() {
		return unique;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query.getKind());
		builder.append("unique", unique);
		builder.append("query", query);
		return builder.toString();
	}
}
