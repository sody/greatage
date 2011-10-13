package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.*;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeSet implements ChangeSetBuilder, DataStoreCallback<Object> {
	private final GAEDatabase database;
	private final String title;
	private final String author;
	private final String location;

	private String comment;
	private Set<String> context = new HashSet<String>();

	private final List<GAEChange> changes = new ArrayList<GAEChange>();
	private GAEChange lastChange;

	GAEChangeSet(final GAEDatabase database, final String title, final String author, final String location) {
		this.database = database;
		this.title = title;
		this.author = author;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getLocation() {
		return location;
	}

	public String getComment() {
		return comment;
	}

	public String getCheckSum() {
		final StringBuilder builder = new StringBuilder().append(comment);
		for (GAEChange change : changes) {
			builder.append(":").append(change);
		}
		return CheckSumUtils.compositeCheckSum(builder.toString());
	}

	public ChangeSetBuilder comment(final String comment) {
		this.comment = comment;
		return this;
	}

	public ChangeSetBuilder context(final String... context) {
		Collections.addAll(this.context, context);
		return this;
	}

	public InsertBuilder insert(final String entityName) {
		return beginChange(new GAEInsert(this, entityName));
	}

	public UpdateBuilder update(final String entityName) {
		return beginChange(new GAEUpdate(this, entityName));
	}

	public DeleteBuilder delete(final String entityName) {
		return beginChange(new GAEDelete(this, entityName));
	}

	public void end() {
		ensureChangeClosed();
		database.endChangeSet(this);
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		for (GAEChange change : changes) {
			change.doInDataStore(dataStore);
		}
		return null;
	}

	<T extends GAEChange> T beginChange(final T change) {
		ensureChangeClosed();
		lastChange = change;
		return change;
	}

	<T extends GAEChange> GAEChangeSet endChange(final T change) {
		changes.add(change);
		lastChange = null;
		return this;
	}

	private void ensureChangeClosed() {
		if (lastChange != null) {
			lastChange.end();
			lastChange = null;
		}
	}
}
