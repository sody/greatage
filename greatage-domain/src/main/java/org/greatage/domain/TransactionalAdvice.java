/*
 * Copyright 2011 Ivan Khalopik
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
