package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSet;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDelete extends GAEChange implements ChangeSet.Delete {
	private final Query query;

	GAEDelete(final String entityName) {
		this.query = new Query(entityName);
	}

	public ChangeSet.Delete where(final ChangeSet.Condition condition) {
		final GAECondition gaeCondition = (GAECondition) condition;
		for (Query.FilterPredicate predicate : gaeCondition.getFilter()) {
			query.addFilter(predicate.getPropertyName(), predicate.getOperator(), predicate.getValue());
		}
		return this;
	}

	public void doInDataStore(final DatastoreService dataStore) {
		for (Entity entity : dataStore.prepare(query).asIterable()) {
			dataStore.delete(entity.getKey());
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query.getKind());
		builder.append("query", query);
		return builder.toString();
	}
}
