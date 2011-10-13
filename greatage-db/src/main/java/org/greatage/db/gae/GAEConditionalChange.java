package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEConditionalChange extends GAEChange {
	private final List<Query.FilterPredicate> conditions = new ArrayList<Query.FilterPredicate>();

	GAEConditionalChange(final GAEChangeSet changeSet) {
		super(changeSet);
	}

	void addCondition(final Query.FilterPredicate condition) {
		conditions.add(condition);
	}

	List<Query.FilterPredicate> getConditions() {
		return conditions;
	}
}
