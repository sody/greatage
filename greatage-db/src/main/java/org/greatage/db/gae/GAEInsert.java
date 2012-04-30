package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.greatage.db.ChangeLog;
import org.greatage.util.DescriptionBuilder;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEInsert extends GAEChange implements ChangeLog.Insert {
    private final String name;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final List<String> into = new ArrayList<String>();
    private final List<List<Object>> values = new ArrayList<List<Object>>();

    GAEInsert(final String name) {
        this.name = name;
    }

    public GAEInsert set(final String propertyName, final Object value) {
        properties.put(propertyName, value);
        return this;
    }

    public GAEInsert set(final String propertyName, final ChangeLog.Select select) {
        properties.put(propertyName, select);
        return this;
    }

    public GAEInsert into(final String... propertyNames) {
        into.addAll(Arrays.asList(propertyNames));
        return this;
    }

    public GAEInsert values(final Object... values) {
        this.values.add(Arrays.asList(values));
        return this;
    }

    protected void execute(final DatastoreService store) {
        final Entity prototype = getPrototype(store, name, properties);

        if (values.isEmpty()) {
            store.put(prototype);
        } else {
            final int parentPropertyIndex = into.indexOf(PARENT_PROPERTY);
            final List<Entity> clonedEntities = new ArrayList<Entity>();
            for (List<Object> valueSet : values) {
                final Key parentKey = parentPropertyIndex >= 0 ?
                        (Key) resolve(store, valueSet.get(parentPropertyIndex)) :
                        prototype.getParent();
                final Entity clonedEntity = new Entity(prototype.getKind(), parentKey);
                clonedEntity.setPropertiesFrom(prototype);
                for (int i = 0; i < into.size(); i++) {
                    if (i != parentPropertyIndex) {
                        final Object value = resolve(store, valueSet.get(i));
                        clonedEntity.setProperty(into.get(i), value);
                    }
                }
                clonedEntities.add(clonedEntity);
            }
            store.put(clonedEntities);
        }
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(this);
        builder.append(name);
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
