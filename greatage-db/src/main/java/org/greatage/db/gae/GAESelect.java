package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSet;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAESelect implements ChangeSet.Select {
	private final Query query;
	private boolean unique;

	GAESelect(final String entityName) {
		query = new Query(entityName);
	}

	public ChangeSet.Select unique() {
		unique = true;
		return this;
	}

	public ChangeSet.Select where(final ChangeSet.Condition condition) {
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
