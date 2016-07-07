package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface DataStoreCallback {

	void doInDataStore(DatastoreService dataStore);
}
