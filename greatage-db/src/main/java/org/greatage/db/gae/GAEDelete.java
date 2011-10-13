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
public class GAEDelete extends GAEConditionalChange implements ChangeSetBuilder.DeleteBuilder {
	private final String entityName;

	GAEDelete(final GAEChangeSet changeSet, final String entityName) {
		super(changeSet);
		this.entityName = entityName;
	}

	public ChangeSetBuilder.ConditionEntryBuilder<ChangeSetBuilder.DeleteBuilder> where(final String propertyName) {
		return new GAECondition<ChangeSetBuilder.DeleteBuilder>(this).and(propertyName);
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		final Query query = new Query(entityName);
		for (Query.FilterPredicate filter : getConditions()) {
			query.addFilter(filter.getPropertyName(), filter.getOperator(), filter.getValue());
		}
		for (Entity entity : dataStore.prepare(query).asIterable()) {
			dataStore.delete(entity.getKey());
		}
		return null;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entityName);
		final DescriptionBuilder conditionsBuilder = new DescriptionBuilder("Conditions");
		for (Query.FilterPredicate filter : getConditions()) {
			conditionsBuilder.append(filter);
		}
		builder.append("conditions", conditionsBuilder);
		return builder.toString();
	}
}
