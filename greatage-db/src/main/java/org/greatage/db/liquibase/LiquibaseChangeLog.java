package org.greatage.db.liquibase;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.greatage.db.ChangeLog;
import org.greatage.db.ChangeLogSupport;
import org.greatage.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LiquibaseChangeLog implements ChangeLog {
	private final DataSource dataSource;
	private final ResourceAccessor resourceAccessor;

	public LiquibaseChangeLog(final DataSource dataSource) {
		this(dataSource, new ClassLoaderResourceAccessor());
	}

	public LiquibaseChangeLog(final DataSource dataSource, final ResourceAccessor resourceAccessor) {
		this.dataSource = dataSource;
		this.resourceAccessor = resourceAccessor;
	}

	public void update(final ChangeLogSupport changeLog, final String... context) {
		options().context(context).update(changeLog);
	}

	public Options options() {
		return new LiquibaseUpdateOptions();
	}

	public ChangeSet changeSet(final String id) {
		throw new UnsupportedOperationException("Liquibase implementation has its own change set structure");
	}

	private void update(final ChangeLogSupport changeLog, final LiquibaseUpdateOptions options) {
		Liquibase liquibase = null;
		try {
			liquibase = createLiquibase(((LiquibaseChangeLogSupport) changeLog).getChangeLog());
			if (options.dropFirst) {
				liquibase.dropAll();
			}
			final StringBuilder contexts = new StringBuilder();
			for (String entry : options.context) {
				if (contexts.length() > 0) {
					contexts.append(',');
				}
				contexts.append(entry);
			}
			liquibase.update(contexts.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (liquibase != null) {
				try {
					liquibase.forceReleaseLocks();
				} catch (LiquibaseException e) {
//					log.warning("Error releasing locks", e);
				}
				final liquibase.database.Database database = liquibase.getDatabase();
				try {
					if (!database.isAutoCommit()) {
						database.rollback();
					}
					database.close();
				} catch (Exception e) {
//					log.warning("problem closing database", e);
				}
			}
		}
	}

	protected Liquibase createLiquibase(final String changeLog) throws Exception {
		return new Liquibase(changeLog, resourceAccessor, getDatabase());
	}

	protected liquibase.database.Database getDatabase() throws Exception {
		return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(getDatabaseConnection());
	}

	protected DatabaseConnection getDatabaseConnection() throws Exception {
		return new JdbcConnection(dataSource.getConnection());
	}

	class LiquibaseUpdateOptions implements Options {
		private final Set<String> context = CollectionUtils.newSet();
		private boolean dropFirst;
		private boolean clearCheckSums;

		public Options dropFirst() {
			dropFirst = true;
			return this;
		}

		public Options clearCheckSums() {
			clearCheckSums = true;
			return this;
		}

		public Options context(final String... context) {
			Collections.addAll(this.context, context);
			return this;
		}

		public void update(final ChangeLogSupport changeLog) {
			LiquibaseChangeLog.this.update(changeLog, this);
		}
	}
}
