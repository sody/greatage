package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultDatabaseService implements DatabaseService {
	private final Database database;
	private final ChangeLog changeLog;

	public DefaultDatabaseService(final Database database, final ChangeLog changeLog) {
		this.database = database;
		this.changeLog = changeLog;
	}

	public void update() {
		database.update(changeLog);
	}
}
