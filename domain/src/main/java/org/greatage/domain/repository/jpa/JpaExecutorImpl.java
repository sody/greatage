/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class JpaExecutorImpl implements JpaExecutor {
	private final EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public JpaExecutorImpl(final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public <T> T execute(final JpaCallback<T> callback) {
		try {
			final EntityManager entityManager = getEntityManager();
			return callback.doInJpa(entityManager);
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	private EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}
}
