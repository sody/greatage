package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.CheckSumUtils;
import org.greatage.db.Database;
import org.greatage.db.Trick;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeSet implements Database.ChangeSet, DataStoreCallback {
	private final GAETrick trick = new GAETrick();

	private final String title;
	private String author = "<unknown>";
	private String location = "<unknown>";

	private final Set<String> context = new HashSet<String>();
	private String comment;
	private String checkSum;

	GAEChangeSet(final String title) {
		this.title = title;
	}

	public Database.ChangeSet author(final String author) {
		this.author = author;
		return this;
	}

	public Database.ChangeSet location(final String location) {
		this.location = location;
		return this;
	}

	public Database.ChangeSet comment(final String comment) {
		this.comment = comment;
		return this;
	}

	public Database.ChangeSet context(final String... context) {
		Collections.addAll(this.context, context);
		return this;
	}

	public Trick trick() {
		return trick;
	}

	String getTitle() {
		return title;
	}

	String getAuthor() {
		return author;
	}

	String getLocation() {
		return location;
	}

	String getComment() {
		return comment;
	}

	String getCheckSum() {
		if (checkSum == null) {
			checkSum = CheckSumUtils.compositeCheckSum(toString());
		}
		return checkSum;
	}

	boolean supports(final Set<String> runContext) {
		return context.isEmpty() || context.containsAll(runContext);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(title).append(author).append(location);
		if (!context.isEmpty()) {
			builder.append("context", context);
		}
		if (!StringUtils.isEmpty(comment)) {
			builder.append("comment", comment);
		}
		builder.append(trick);
		return builder.toString();
	}

	public void doInDataStore(final DatastoreService dataStore) {
		trick.doInDataStore(dataStore);
	}
}
