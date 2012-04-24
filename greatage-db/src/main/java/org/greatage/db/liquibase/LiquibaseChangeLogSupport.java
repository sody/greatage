package org.greatage.db.liquibase;

import org.greatage.db.ChangeLogSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LiquibaseChangeLogSupport extends ChangeLogSupport {
	private final String changeLog;

	public LiquibaseChangeLogSupport(final String changeLog) {
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
