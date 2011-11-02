package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSetBuilder;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDelete extends GAEChange implements ChangeSetBuilder.DeleteBuilder, GAEConditional {
	private final Query query;

	GAEDelete(final GAEChangeSet changeSet, final String entityName) {
		super(changeSet);
		this.query = new Query(entityName);
	}

	public ChangeSetBuilder.ConditionEntryBuilder<ChangeSetBuilder.DeleteBuilder> where(final String propertyName) {
		return new GAECondition<ChangeSetBuilder.DeleteBuilder>(this).and(propertyName);
	}

	public void addCondition(final Query.FilterPredicate condition) {
		query.addFilter(condition.getPropertyName(), condition.getOperator(), condition.getValue());
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		for (Entity entity : dataStore.prepare(query).asIterable()) {
			dataStore.delete(entity.getKey());
		}
		return null;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query.getKind());
		builder.append("query", query);
		return builder.toString();
	}
}
