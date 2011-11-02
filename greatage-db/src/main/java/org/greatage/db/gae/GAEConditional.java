package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface GAEConditional {

	void addCondition(Query.FilterPredicate condition);

}
