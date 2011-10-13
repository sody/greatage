package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ConditionEntryBuilder;
import org.greatage.db.UpdateBuilder;
import org.greatage.util.DescriptionBuilder;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEUpdate extends GAEConditionalChange implements UpdateBuilder {
	private final Entity entity;

	GAEUpdate(final GAEChangeSet changeSet, final String entityName) {
		super(changeSet);
		this.entity = new Entity(entityName);
	}

	public UpdateBuilder set(final String propertyName, final Object value) {
		entity.setProperty(propertyName, value);
		return this;
	}

	public ConditionEntryBuilder<UpdateBuilder> where(final String property) {
		return new GAECondition<UpdateBuilder>(this).and(property);
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		final Query query = new Query(entity.getKind());
		for (Query.FilterPredicate filter : getConditions()) {
			query.addFilter(filter.getPropertyName(), filter.getOperator(), filter.getValue());
		}

		for (Entity realEntity : dataStore.prepare(query).asIterable()) {
			realEntity.setPropertiesFrom(entity);
			dataStore.put(realEntity);
		}

		return null;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entity.getKind());
		for (Map.Entry<String, Object> entry : entity.getProperties().entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		final DescriptionBuilder conditionsBuilder = new DescriptionBuilder("Conditions");
		for (Query.FilterPredicate filter : getConditions()) {
			conditionsBuilder.append(filter);
		}
		builder.append("conditions", conditionsBuilder);
		return builder.toString();
	}
}
