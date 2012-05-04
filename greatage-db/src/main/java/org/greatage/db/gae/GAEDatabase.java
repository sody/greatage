package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import org.greatage.db.Database;
import org.greatage.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDatabase implements Database, GAEConstants {
    private final DatastoreService store;

    private final Set<String> context = CollectionUtils.newSet();
    private boolean dropFirst;
    private boolean clearCheckSums;

    public GAEDatabase() {
        this(DatastoreServiceFactory.getDatastoreService());
    }

    public GAEDatabase(final DatastoreService store) {
        this.store = store;
    }

    public Database dropFirst() {
        dropFirst = true;
        return this;
    }

    public Database clearCheckSums() {
        clearCheckSums = true;
        return this;
    }

    public Database context(final String... context) {
        Collections.addAll(this.context, context);
        return this;
    }

    public Database update(final Script script) {
        final GAEChangeLog log = new GAEChangeLog(store, context, clearCheckSums);

        new GAELock().execute(store);
        try {
            if (dropFirst) {
                // delete all non system tables
                new GAEDeleteAll().execute(store);
                // delete log table
                new GAEDelete(LOG_TABLE).execute(store);
            }

            script.execute(log);

            // ensure that last operation finished
            log.end();
        } finally {
            new GAEUnlock().execute(store);
        }
        return this;
    }
}
