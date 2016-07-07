package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.Trick;
import org.greatage.util.DescriptionBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class GAETrick implements Trick, DataStoreCallback {
	private final List<GAEChange> changes = new ArrayList<GAEChange>();

	public Insert insert(final String entityName) {
		return addChange(new GAEInsert(entityName));
	}

	public Update update(final String entityName) {
		return addChange(new GAEUpdate(entityName));
	}

	public Delete delete(final String entityName) {
		return addChange(new GAEDelete(entityName));
	}

	public Select select(final String entityName) {
		return new GAESelect(entityName);
	}

	public ConditionEntry condition(final String propertyName) {
		return new GAEConditionEntry(propertyName);
	}

	public void doInDataStore(final DatastoreService dataStore) {
		for (GAEChange change : changes) {
			change.doInDataStore(dataStore);
		}
	}

	private <T extends GAEChange> T addChange(final T change) {
		changes.add(change);
		return change;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		if (!changes.isEmpty()) {
			builder.append("changes", changes);
		}
		return builder.toString();
	}
}
