/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class JdoExecutorImpl implements JdoExecutor {
	private final PersistenceManagerFactory persistenceManagerFactory;

	private PersistenceManager persistenceManager;

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
}
