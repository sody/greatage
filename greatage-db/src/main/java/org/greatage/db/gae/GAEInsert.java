package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.greatage.db.Trick;
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
public class GAEInsert extends GAEChange implements Trick.Insert {
	private final Entity entity;
	private final Map<String, Trick.Select> setters = new HashMap<String, Trick.Select>();
	private final List<String> into = new ArrayList<String>();
	private final List<Entity> values = new ArrayList<Entity>();

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
		into.addAll(Arrays.asList(propertyNames));
		return this;
	}

	public Trick.Insert values(final Object... values) {
		final Entity value = new Entity(entity.getKind());
		for (int i = 0; i < into.size(); i++) {
			value.setProperty(into.get(i), values[i]);
		}
		return this;
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
		if (values.isEmpty()) {
			dataStore.put(entity);
		} else {
			for (Entity value : values) {
				value.setPropertiesFrom(entity);
			}
			dataStore.put(values);
		}
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
		if (!values.isEmpty()) {
			builder.append("into", into);
			for (Entity value : values) {
				final StringBuilder sb = new StringBuilder();
				for (String propertyName : into) {
					if (sb.length() > 0) {
						sb.append(',');
					}
					sb.append(value.getProperty(propertyName));
				}
				builder.append("values", sb);
			}
		}
		return builder.toString();
	}
}
