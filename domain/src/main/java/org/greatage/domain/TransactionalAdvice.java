/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.greatage.domain.annotations.Transactional;
import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TransactionalAdvice implements MethodAdvice {
	private final TransactionExecutor executor;

	public TransactionalAdvice(final TransactionExecutor executor) {
		this.executor = executor;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Transactional transactional = invocation.getAnnotation(Transactional.class);
		if (transactional != null) {
			final Transaction transaction = executor.begin();
			try {
				final Object result = invocation.proceed(parameters);
				transaction.commit();
				return result;
			} catch (Throwable throwable) {
				transaction.rollback();
				throw throwable;
			}
		}
		return invocation.proceed(parameters);
	}

}
