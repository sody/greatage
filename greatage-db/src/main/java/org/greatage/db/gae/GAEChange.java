package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEChange implements DataStoreCallback {
	public static final String PARENT_PROPERTY = "__parent__";

	protected void setProperty(final DatastoreService dataStore, final Entity prototype, final String propertyName, final Object value) {
		if (!PARENT_PROPERTY.equals(propertyName)) {
			if (value instanceof GAESelect) {
				final GAESelect selectQuery = (GAESelect) value;
				if (!selectQuery.isUnique()) {
					final List<Key> keys = new ArrayList<Key>();
					for (Entity selected : dataStore.prepare(selectQuery.getQuery()).asList(FetchOptions.Builder.withDefaults())) {
						keys.add(selected.getKey());
					}
					prototype.setProperty(propertyName, keys);
				} else {
					final Entity entity = dataStore.prepare(selectQuery.getQuery()).asSingleEntity();
					prototype.setProperty(propertyName, entity != null ? entity.getKey() : null);
				}
			} else {
				prototype.setProperty(propertyName, value);
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
