package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface GAESettable {

	void addSelect(String name, Query query);
}
