package org.greatage.db.liquibase;

import org.greatage.db.ChangeLog;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LiquibaseChangeLog extends ChangeLog {
	private final String changeLog;

	public LiquibaseChangeLog(final String changeLog) {
		this.changeLog = changeLog;
	}

	public String getChangeLog() {
		return changeLog;
	}

	@Override
	protected void init() {
		// do nothing
	}
}
