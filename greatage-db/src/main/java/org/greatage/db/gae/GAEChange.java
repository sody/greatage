package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.greatage.util.DescriptionBuilder;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEChange {
    public static final String PARENT_PROPERTY = "__parent__";

    protected abstract void execute(DatastoreService store);

    protected Object resolve(final DatastoreService store, final Object value) {
        return value != null && value instanceof GAESelect ? ((GAESelect) value).get(store) : value;
    }

    protected Entity getPrototype(final DatastoreService store, final String name, final Map<String, Object> properties) {
        final Entity prototype = properties.containsKey(PARENT_PROPERTY) ?
                new Entity(name, (Key) resolve(store, properties.get(PARENT_PROPERTY))) :
                new Entity(name);
        for (Map.Entry<String, Object> property : properties.entrySet()) {
            if (!PARENT_PROPERTY.equals(property.getKey())) {
                prototype.setProperty(property.getKey(), property.getValue());
            }
        }
        return prototype;
    }

    @Override
    public String toString() {
        return new DescriptionBuilder(this).toString();
    }
}
