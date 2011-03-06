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

import org.greatage.domain.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateExecutorImpl implements HibernateExecutor {
	private final SessionFactory sessionFactory;

	private Session session;

	public HibernateExecutorImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public <T> T execute(final HibernateCallback<T> callback) {
		try {
			final Session session = getSession();
			final T result = callback.doInSession(session);
			session.flush();
			return result;
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	public void clear() {
		if (session != null) {
			session.close();
			session = null;
		}
	}

	public Transaction begin() {
		return new HibernateTransaction(getSession().beginTransaction());
	}

	private Session getSession() {
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}
}
