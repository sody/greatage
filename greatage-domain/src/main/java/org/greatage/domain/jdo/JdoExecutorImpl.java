/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.domain.jdo;

import org.greatage.domain.Transaction;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
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

	public Transaction begin() {
		final javax.jdo.Transaction transaction = getPersistenceManager().currentTransaction();
		transaction.begin();
		return new JdoTransaction(transaction);
	}
}
