package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAESelect implements ChangeLog.Select {
    private final String name;
    private boolean keys;
    private boolean unique;
    private GAECondition condition;

    GAESelect(final String name) {
        this.name = name;
    }

    public GAESelect unique() {
        unique = true;
        return this;
    }

    public GAESelect keys() {
        keys = true;
        return this;
    }

    public GAESelect where(final ChangeLog.Condition condition) {
        this.condition = (GAECondition) condition;
        return this;
    }

    Iterable<Entity> iterate(final DatastoreService store) {
        return prepare(store).asIterable();
    }

    Object get(final DatastoreService store) {
        if (unique) {
            final Entity value = prepare(store).asSingleEntity();
            return keys && value != null ? value.getKey() : value;
        } else {
            final List<Object> result = new ArrayList<Object>();
            for (Entity selected : prepare(store).asIterable()) {
                result.add(keys ? selected.getKey() : selected);
            }
            return result;
        }
    }

    private PreparedQuery prepare(final DatastoreService store) {
        final Query query = new Query(name);
        if (condition != null) {
            condition.apply(query);
        }
        if (keys) {
            query.setKeysOnly();
        }
        return store.prepare(query);
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(this);
        builder.append(name);
        builder.append("unique", unique);
        builder.append("condition", condition);
        return builder.toString();
    }
}
