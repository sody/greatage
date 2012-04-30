package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import org.greatage.db.ChangeLog;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDelete extends GAEChange implements ChangeLog.Delete {
    private final String name;
    private final GAESelect select;

    GAEDelete(final String name) {
        this.name = name;
        select = new GAESelect(name).keys();
    }

    public ChangeLog.Delete where(final ChangeLog.Condition condition) {
        select.where(condition);
        return this;
    }

    protected void execute(final DatastoreService store) {
        for (Entity entity : select.iterate(store)) {
            store.delete(entity.getKey());
        }
    }

    @Override
    public String toString() {
        final DescriptionBuilder builder = new DescriptionBuilder(this);
        builder.append(name);
        builder.append("select", select);
        return builder.toString();
    }
}
