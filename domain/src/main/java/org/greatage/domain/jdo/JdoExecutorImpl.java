/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoExecutorImpl implements JdoExecutor {
	private final PersistenceManagerFactory persistenceManagerFactory;

	private PersistenceManager persistenceManager;
	private Transaction transaction;

	public JdoExecutorImpl(final PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	public <T> T execute(final JdoCallback<T> callback) {
		try {
			final PersistenceManager persistenceManager = getPersistenceManager();
			return callback.doInJdo(persistenceManager);
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	private PersistenceManager getPersistenceManager() {
		if (persistenceManager == null) {
			persistenceManager = persistenceManagerFactory.getPersistenceManager();
		}
		return persistenceManager;
	}

	public void begin() {
		transaction = getPersistenceManager().currentTransaction();
		transaction.begin();
	}

	public void commit() {
		transaction.commit();
	}

	public void rollback() {
		transaction.rollback();
	}
}
