package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDeleteAll extends GAEChange implements GAEConstants {

    @Override
    protected void execute(final DatastoreService store) {
        for (Entity kindMetadata : new GAESelect(Query.KIND_METADATA_KIND).iterate(store)) {
            final String kind = kindMetadata.getKey().getName();
            if (!LOCK_TABLE.equals(kind) && !LOG_TABLE.equals(kind)) {
                new GAEDelete(kind).execute(store);
            }
        }
    }

    @Override
    public String toString() {
        return new DescriptionBuilder(this).toString();
    }
}
