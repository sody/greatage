/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.greatage.domain.annotations.Transactional;
import org.greatage.ioc.services.Invocation;
import org.greatage.ioc.services.MethodAdvice;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TransactionalAdvice implements MethodAdvice {
	private final EntityTransactionManager transactionManager;

	public TransactionalAdvice(final EntityTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Transactional transactional = invocation.getAnnotation(Transactional.class);
		if (transactional != null) {
			try {
				transactionManager.begin();
				final Object result = invocation.proceed(parameters);
				transactionManager.commit();
				return result;
			} catch (Throwable throwable) {
				transactionManager.rollback();
				throw throwable;
			}
		}
		return invocation.proceed(parameters);
	}

}
