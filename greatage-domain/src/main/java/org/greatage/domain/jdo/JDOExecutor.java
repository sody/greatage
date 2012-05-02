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

import org.greatage.domain.TransactionExecutor;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDOExecutor implements TransactionExecutor<Transaction, PersistenceManager> {
	private final PersistenceManagerFactory persistenceManagerFactory;
	private final ThreadLocal<PersistenceManager> sessionHolder = new ThreadLocal<PersistenceManager>();

	public JDOExecutor(final PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	public <V> V execute(final TransactionCallback<V, Transaction> callback) {
		return execute(new SessionCallback<V, PersistenceManager>() {
			public V doInSession(final PersistenceManager session) throws Exception {
				Transaction transaction = null;
				try {
					transaction = session.currentTransaction();
					transaction.begin();
					final V result = callback.doInTransaction(transaction);
					transaction.commit();
					return result;
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
				}
			}
		});
	}

	public <V> V execute(final SessionCallback<V, PersistenceManager> callback) {
		PersistenceManager session = sessionHolder.get();
		final boolean sessionCreated = session == null;
		try {
			if (session == null) {
				session = persistenceManagerFactory.getPersistenceManager();
				sessionHolder.set(session);
			}
			final V result = callback.doInSession(session);
			session.flush();
			return result;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sessionCreated && session != null) {
				session.close();
				sessionHolder.remove();
			}
		}
	}
}
