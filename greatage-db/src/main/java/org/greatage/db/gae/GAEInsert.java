package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.greatage.db.ChangeSet;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEInsert extends GAEChange implements ChangeSet.Insert {
	private final String entityName;
	private final Map<String, Object> properties = new HashMap<String, Object>();
	private final List<String> into = new ArrayList<String>();
	private final List<List<Object>> values = new ArrayList<List<Object>>();

	GAEInsert(final String entityName) {
		this.entityName = entityName;
	}

	public ChangeSet.Insert set(final String propertyName, final Object value) {
		properties.put(propertyName, value);
		return this;
	}

	public ChangeSet.Insert set(final String propertyName, final ChangeSet.Select select) {
		properties.put(propertyName, select);
		return this;
	}

	public ChangeSet.Insert into(final String... propertyNames) {
		into.addAll(Arrays.asList(propertyNames));
		return this;
	}

	public ChangeSet.Insert values(final Object... values) {
		this.values.add(Arrays.asList(values));
		return this;
	}

	public void doInDataStore(final DatastoreService dataStore) {
		final Entity prototype = properties.containsKey(PARENT_PROPERTY)
				? new Entity(entityName, dataStore.prepare(((GAESelect) properties.get(PARENT_PROPERTY)).getQuery()).asSingleEntity().getKey())
				: new Entity(entityName);

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			setProperty(dataStore, prototype, entry.getKey(), entry.getValue());
		}

		if (values.isEmpty()) {
			dataStore.put(prototype);
		} else {
			final int parentPropertyIndex = into.indexOf(PARENT_PROPERTY);
			final List<Entity> clonedEntities = new ArrayList<Entity>();
			for (List<Object> value : values) {
				final Key parentKey = parentPropertyIndex >= 0
						? dataStore.prepare(((GAESelect) value.get(parentPropertyIndex)).getQuery()).asSingleEntity().getKey()
						: prototype.getParent();
				final Entity clonedEntity = new Entity(prototype.getKind(), parentKey);
				clonedEntity.setPropertiesFrom(prototype);
				for (int i = 0; i < into.size(); i++) {
					setProperty(dataStore, clonedEntity, into.get(i), value.get(i));
				}
				clonedEntities.add(clonedEntity);
			}
			dataStore.put(clonedEntities);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entityName);
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		if (!values.isEmpty()) {
			builder.append("into", into);
			for (List<Object> value : values) {
				builder.append("values", value);
			}
		}
		return builder.toString();
	}
}
