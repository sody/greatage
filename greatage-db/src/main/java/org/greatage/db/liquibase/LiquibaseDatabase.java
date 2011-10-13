package org.greatage.db.liquibase;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.greatage.db.ChangeLog;
import org.greatage.db.ChangeSetBuilder;
import org.greatage.db.Database;

import javax.sql.DataSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LiquibaseDatabase implements Database {
	private boolean skip;
	private boolean dropFirst;
	private String contexts;
	private String changeLog;

	private final DataSource dataSource;
	private final ResourceAccessor resourceAccessor;

	public LiquibaseDatabase(final DataSource dataSource) {
		this(dataSource, new ClassLoaderResourceAccessor());
	}

	public LiquibaseDatabase(final DataSource dataSource, final ResourceAccessor resourceAccessor) {
		this.dataSource = dataSource;
		this.resourceAccessor = resourceAccessor;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(final boolean skip) {
		this.skip = skip;
	}

	public boolean isDropFirst() {
		return dropFirst;
	}

	public void setDropFirst(final boolean dropFirst) {
		this.dropFirst = dropFirst;
	}

	public String getContexts() {
		return contexts;
	}

	public void setContexts(final String contexts) {
		this.contexts = contexts;
	}

	public void update(final ChangeLog changeLog) {
		if (isSkip()) {
//			log.info("LiquiBase skipped due to skip flag configuration");
			return;
		}

		Liquibase liquibase = null;
		try {
			liquibase = createLiquibase(((LiquibaseChangeLog)changeLog).getChangeLog());
			if (isDropFirst()) {
				liquibase.dropAll();
			}
			liquibase.update(getContexts());
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

	public ChangeSetBuilder changeSet(final String id, final String author, final String location) {
		throw new UnsupportedOperationException("Liquibase implementation has its own change set structure");
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
}
