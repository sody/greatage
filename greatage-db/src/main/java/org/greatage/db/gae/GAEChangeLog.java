package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.greatage.db.ChangeLogSupport;
import org.greatage.db.CheckSumUtils;
import org.greatage.db.DatabaseException;
import org.greatage.util.CollectionUtils;
import org.greatage.util.CompositeKey;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeLog implements ChangeLog {
	private final Set<CompositeKey> ranChangeSets = CollectionUtils.newSet();
	private final DatastoreService dataStore;

	private GAEOptions options;
	private GAEChangeSet lastChangeSet;

	public GAEChangeLog() {
		this(DatastoreServiceFactory.getDatastoreService());
	}

	public GAEChangeLog(final DatastoreService dataStore) {
		this.dataStore = dataStore;
	}

	public void update(final ChangeLogSupport changeLog, final String... context) {
		options().context(context).update(changeLog);
	}

	public Options options() {
		return new GAEOptions();
	}

	public ChangeSet changeSet(final String id) {
		return begin(new GAEChangeSet(id));
	}

	private synchronized void update(final ChangeLogSupport changeLog, final GAEOptions options) {
		this.options = options;
		lock();
		try {
			if (options.dropFirst) {
				dropAll();
			}

			changeLog.execute(this);

			commit();
		} finally {
			this.options = null;
			lastChangeSet = null;
			ranChangeSets.clear();
			unlock();
		}
	}

	GAEChangeSet begin(final GAEChangeSet changeSet) {
		commit();
		this.lastChangeSet = changeSet;
		return changeSet;
	}

	void end(final GAEChangeSet changeSet) {
		if (changeSet.supports(options.context)) {
			final String checkSum = changeSet.getCheckSum();
			System.out.println("Executing ChangeSet: " + changeSet.toString());
			System.out.println("CheckSum : " + checkSum);

			final CompositeKey key = new CompositeKey(changeSet.getTitle(), changeSet.getAuthor(), changeSet.getLocation());
			if (ranChangeSets.contains(key)) {
				throw new DatabaseException(String.format("ChangeSet '%s' has already been executed", changeSet));
			}
			ranChangeSets.add(key);

			final Query query = new Query(SystemTables.LOG.NAME)
					.addFilter(SystemTables.LOG.TITLE, Query.FilterOperator.EQUAL, changeSet.getTitle())
					.addFilter(SystemTables.LOG.AUTHOR, Query.FilterOperator.EQUAL, changeSet.getAuthor())
					.addFilter(SystemTables.LOG.LOCATION, Query.FilterOperator.EQUAL, changeSet.getLocation());
			final Entity logEntry = dataStore.prepare(query).asSingleEntity();
			if (logEntry == null) {
				changeSet.doInDataStore(dataStore);
				log(changeSet, checkSum);
			} else {
				final String expectedCheckSum = (String) logEntry.getProperty(SystemTables.LOG.CHECKSUM);
				if (options.clearCheckSums || !CheckSumUtils.isValid(expectedCheckSum)) {
					logEntry.setProperty(SystemTables.LOG.CHECKSUM, checkSum);
					dataStore.put(logEntry);
				} else if (!expectedCheckSum.equals(checkSum)) {
					throw new DatabaseException(String.format("CheckSum check failed for change set '%s'. Should be '%s' but was '%s'",
							changeSet, expectedCheckSum, checkSum));
				}
			}
			this.lastChangeSet = null;
		}
	}

	private void commit() {
		if (lastChangeSet != null) {
			end(lastChangeSet);
			lastChangeSet = null;
		}
	}

	private void lock() {
		Entity lock = null;
		try {
			lock = dataStore.get(KeyFactory.createKey(SystemTables.LOCK.NAME, SystemTables.LOCK.ID));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock == null) {
			lock = new Entity(SystemTables.LOCK.NAME, SystemTables.LOCK.ID);
		}
		if (lock.hasProperty(SystemTables.LOCK.LOCKED_AT)) {
			//noinspection MalformedFormatString
			throw new DatabaseException(String.format("Already locked at '%1$tF %1$tT'. Skipping update.",
					lock.getProperty(SystemTables.LOCK.LOCKED_AT)));
		}
		lock.setProperty(SystemTables.LOCK.LOCKED_AT, new Date());
		dataStore.put(lock);
	}

	private void unlock() {
		Entity lock = null;
		try {
			lock = dataStore.get(KeyFactory.createKey(SystemTables.LOCK.NAME, SystemTables.LOCK.ID));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock != null && lock.hasProperty(SystemTables.LOCK.LOCKED_AT)) {
			lock.removeProperty(SystemTables.LOCK.LOCKED_AT);
			dataStore.put(lock);
		}
	}

	private void log(final GAEChangeSet changeSet, final String checkSum) {
		final Entity logEntry = new Entity(SystemTables.LOG.NAME);
		logEntry.setProperty(SystemTables.LOG.TITLE, changeSet.getTitle());
		logEntry.setProperty(SystemTables.LOG.AUTHOR, changeSet.getAuthor());
		logEntry.setProperty(SystemTables.LOG.LOCATION, changeSet.getLocation());
		logEntry.setProperty(SystemTables.LOG.COMMENT, changeSet.getComment());
		logEntry.setProperty(SystemTables.LOG.CHECKSUM, checkSum);
		logEntry.setProperty(SystemTables.LOG.EXECUTED_AT, new Date());
		dataStore.put(logEntry);
	}

	private void dropAll() {
		for (Entity kindMetadata : dataStore.prepare(new Query(Query.KIND_METADATA_KIND)).asIterable()) {
			final String kind = kindMetadata.getKey().getName();
			if (!SystemTables.LOCK.NAME.equals(kind)) {
				for (Entity entity : dataStore.prepare(new Query(kind).setKeysOnly()).asIterable()) {
					dataStore.delete(entity.getKey());
				}
			}
		}
	}

	class GAEOptions implements Options {
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
			GAEChangeLog.this.update(changeLog, this);
		}
	}

	interface SystemTables {
		interface LOCK {
			long ID = 1l;

			String NAME = "DATABASE_CHANGE_LOG_LOCK";
			String LOCKED_AT = "lockedAt";
		}

		interface LOG {
			String NAME = "DATABASE_CHANGE_LOG";
			String TITLE = "title";
			String AUTHOR = "author";
			String LOCATION = "location";
			String COMMENT = "comment";
			String CHECKSUM = "checkSum";
			String EXECUTED_AT = "executedAt";
		}
	}
}
