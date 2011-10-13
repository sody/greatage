package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import org.greatage.db.InsertBuilder;
import org.greatage.util.DescriptionBuilder;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEInsert extends GAEChange implements InsertBuilder {
	private final Entity entity;

	GAEInsert(final GAEChangeSet changeSet, final String entityName) {
		super(changeSet);
		entity = new Entity(entityName);
	}

	public InsertBuilder set(final String propertyName, final Object value) {
		entity.setProperty(propertyName, value);
		return this;
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		dataStore.put(entity);
		return null;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entity.getKind());
		for (Map.Entry<String, Object> entry : entity.getProperties().entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		return builder.toString();
	}
}
