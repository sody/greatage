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

package org.greatage.domain.jpa;

import org.greatage.domain.TransactionExecutor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JPAExecutor implements TransactionExecutor<EntityTransaction, EntityManager> {
	private final EntityManagerFactory entityManagerFactory;
	private final ThreadLocal<EntityManager> sessionHolder = new ThreadLocal<EntityManager>();

	public JPAExecutor(final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public <V> V execute(final TransactionCallback<V, EntityTransaction> callback) {
		return execute(new SessionCallback<V, EntityManager>() {
			public V doInSession(final EntityManager session) throws Exception {
				EntityTransaction transaction = null;
				try {
					transaction = session.getTransaction();
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

	public <V> V execute(final SessionCallback<V, EntityManager> callback) {
		EntityManager session = sessionHolder.get();
		final boolean sessionCreated = session == null;
		try {
			if (session == null) {
				session = entityManagerFactory.createEntityManager();
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
