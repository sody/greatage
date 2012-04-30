package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import org.greatage.db.ChangeLog;
import org.greatage.util.DescriptionBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEUpdate extends GAEChange implements ChangeLog.Update {
    private final String name;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final GAESelect select;

    GAEUpdate(final String name) {
        this.name = name;
        select = new GAESelect(name);
    }

    public GAEUpdate set(final String propertyName, final Object value) {
        properties.put(propertyName, value);
        return this;
    }

    public GAEUpdate set(final String propertyName, final ChangeLog.Select select) {
        properties.put(propertyName, select);
        return this;
    }

    public GAEUpdate where(final ChangeLog.Condition condition) {
        select.where(condition);
        return this;
    }

    protected void execute(final DatastoreService store) {
        final Entity prototype = getPrototype(store, name, properties);

        for (Entity entity : select.iterate(store)) {
            entity.setPropertiesFrom(prototype);
            store.put(entity);
        }
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(this);
        builder.append(name);
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            builder.append(entry.getKey(), entry.getValue());
        }
        builder.append("select", select);
        return builder.toString();
    }
}
