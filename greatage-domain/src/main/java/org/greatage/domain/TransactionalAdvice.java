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

package org.greatage.domain;

import org.greatage.domain.annotations.Transactional;
import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TransactionalAdvice implements Interceptor {
	private final TransactionExecutor<Object, Object> executor;

	public TransactionalAdvice(final TransactionExecutor<Object, Object> executor) {
		this.executor = executor;
	}

	public boolean supports(final Invocation invocation) {
		return invocation.getMethod().isAnnotationPresent(Transactional.class);
	}

	public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
		return executor.execute(new TransactionCallback<Object, Object>() {
			public Object doInTransaction(final Object transaction) throws Exception {
				try {
					return invocation.proceed(parameters);
				} catch (Exception e) {
					throw e;
				} catch (Throwable throwable) {
					throw new RuntimeException(throwable);
				}
			}
		});
	}
}
