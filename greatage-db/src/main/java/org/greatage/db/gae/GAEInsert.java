package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.greatage.db.Trick;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEInsert extends GAEChange implements Trick.Insert {
	private final Entity entity;
	private final Map<String, Trick.Select> setters = new HashMap<String, Trick.Select>();

	GAEInsert(final String entityName) {
		entity = new Entity(entityName);
	}

	public Trick.Insert set(final String propertyName, final Object value) {
		entity.setProperty(propertyName, value);
		return this;
	}

	public Trick.Insert set(final String propertyName, final Trick.Select select) {
		setters.put(propertyName, select);
		return this;
	}

	public Trick.Insert into(final String... propertyNames) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Trick.Insert values(final Object... values) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void doInDataStore(final DatastoreService dataStore) {
		for (Map.Entry<String, Trick.Select> entry : setters.entrySet()) {
			final GAESelect selectQuery = (GAESelect) entry.getValue();

			if (!selectQuery.isUnique()) {
				final List<Key> keys = new ArrayList<Key>();
				for (Entity selected : dataStore.prepare(selectQuery.getQuery()).asIterable()) {
					keys.add(selected.getKey());
				}
				entity.setProperty(entry.getKey(), keys);
			} else {
				//todo: implement this
			}
		}

		dataStore.put(entity);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(entity.getKind());
		for (Map.Entry<String, Object> entry : entity.getProperties().entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, Trick.Select> entry : setters.entrySet()) {
			builder.append(entry.getKey(), entry.getValue());
		}
		return builder.toString();
	}
}
