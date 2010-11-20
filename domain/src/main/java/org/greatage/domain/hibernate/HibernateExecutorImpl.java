/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
