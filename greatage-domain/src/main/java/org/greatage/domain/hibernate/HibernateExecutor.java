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

package org.greatage.domain.hibernate;

import org.greatage.domain.TransactionExecutor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateExecutor implements TransactionExecutor<Transaction, Session> {
	private final SessionFactory sessionFactory;
	private final ThreadLocal<Session> sessionHolder = new ThreadLocal<Session>();

	public HibernateExecutor(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public <V> V execute(final TransactionCallback<V, Transaction> callback) {
		return execute(new SessionCallback<V, Session>() {
			public V doInSession(final Session session) throws Exception {
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
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

	public <V> V execute(final SessionCallback<V, Session> callback) {
		Session session = sessionHolder.get();
		final boolean sessionCreated = session == null;
		try {
			if (session == null) {
				session = sessionFactory.openSession();
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
