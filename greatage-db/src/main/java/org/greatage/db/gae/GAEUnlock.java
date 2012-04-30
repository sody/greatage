package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEUnlock extends GAEChange implements GAEConstants {

    @Override
    protected void execute(final DatastoreService store) {
        final Entity lock = getLock(store);
        if (lock.hasProperty(LOCKED_AT_COLUMN)) {
            lock.removeProperty(LOCKED_AT_COLUMN);
        }
        store.put(lock);
    }

    private Entity getLock(final DatastoreService store) {
        try {
            return store.get(KeyFactory.createKey(LOCK_TABLE, LOCK_ID));
        } catch (EntityNotFoundException e) {
            // it doesn't exist, so we need to create one
            return new Entity(LOCK_TABLE, LOCK_ID);
        }
    }
}
