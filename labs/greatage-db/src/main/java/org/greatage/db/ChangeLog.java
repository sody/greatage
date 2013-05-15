package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ChangeLog {
	private static final String DEFAULT_AUTHOR = "<unknown>";

	private Database database;
	private Database.ChangeSet changeSet;

	private String author;
	private String location;

	protected ChangeLog() {
		location = getClass().getName();
		author = DEFAULT_AUTHOR;
	}

	public final void execute(final Database database) {
		this.database = database;
		init();
		this.database = null;
	}

	protected void location(final String location) {
		assert location != null;

		this.location = location;
	}

	protected void author(final String author) {
		assert author != null;

		this.author = author;
	}

	protected Database.ChangeSet begin(final String title) {
		assert database != null;
		assert title != null;

		changeSet = database.changeSet(title).author(author).location(location);
		return changeSet;
	}

	protected Trick.Insert insert(final String entityName) {
		return changeSet.trick().insert(entityName);
	}

	protected Trick.Update update(final String entityName) {
		return changeSet.trick().update(entityName);
	}

	protected Trick.Delete delete(final String entityName) {
		return changeSet.trick().delete(entityName);
	}

	protected Trick.Select select(final String entityName) {
		return changeSet.trick().select(entityName);
	}

	protected Trick.ConditionEntry condition(final String propertyName) {
		return changeSet.trick().condition(propertyName);
	}

	protected void add(final ChangeLog changeLog) {
		assert database != null;
		assert changeLog != null;

		changeLog.execute(database);
	}

	protected abstract void init();
}
