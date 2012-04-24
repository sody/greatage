package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeSet;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEUpdate extends GAEChange implements ChangeSet.Update {
	private final Map<String, ChangeSet.Select> setters = new HashMap<String, ChangeSet.Select>();

	private final Entity entity;
	private final Query query;

	GAEUpdate(final String entityName) {
		this.entity = new Entity(entityName);
		this.query = new Query(entityName);
	}

	public ChangeSet.Update set(final String propertyName, final Object value) {
		entity.setProperty(propertyName, value);
		return this;
	}

	public ChangeSet.Update set(final String propertyName, final ChangeSet.Select select) {
		setters.put(propertyName, select);
		return this;
	}

	public ChangeSet.Update where(final ChangeSet.Condition condition) {
		final GAECondition gaeCondition = (GAECondition) condition;
		for (Query.FilterPredicate predicate : gaeCondition.getFilter()) {
			query.addFilter(predicate.getPropertyName(), predicate.getOperator(), predicate.getValue());
		}
		return this;
	}

	public void doInDataStore(final DatastoreService dataStore) {
		for (Map.Entry<String, ChangeSet.Select> entry : setters.entrySet()) {
			final GAESelect selectQuery = (GAESelect) entry.getValue();

			if (!selectQuery.isUnique()) {
				final List<Key> keys = new ArrayList<Key>();
				for (Entity selected : dataStore.prepare(selectQuery.getQuery()).asIterable()) {
					keys.add(selected.getKey());
				}
				entity.setProperty(entry.getKey(), keys);
			} else {
				final Entity value = dataStore.prepare(selectQuery.getQuery()).asSingleEntity();
				entity.setProperty(entry.getKey(), value != null ? value.getKey() : null);
			}
		}

		for (Entity realEntity : dataStore.prepare(query).asIterable()) {
			realEntity.setPropertiesFrom(entity);
			dataStore.put(realEntity);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query.getKind());
		for (Map.Entry<String, Object> entry : entity.getProperties().entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, ChangeSet.Select> entry : setters.entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		builder.append("query", query);
		return builder.toString();
	}
}
