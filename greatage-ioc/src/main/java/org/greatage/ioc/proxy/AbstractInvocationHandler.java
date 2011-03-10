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

package org.greatage.ioc.proxy;

import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @param <T> delegate type
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractInvocationHandler<T> {
	private final ObjectBuilder<T> builder;
	private final List<Interceptor> interceptors;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder	  object builder
	 * @param interceptors method interceptors
	 */
	protected AbstractInvocationHandler(final ObjectBuilder<T> builder, final List<Interceptor> interceptors) {
		assert builder != null;

		this.builder = builder;
		this.interceptors = interceptors;
	}

	/**
	 * Gets object instance. If instance is not initialized, creates it from object builder.
	 *
	 * @return lazy initialized object instance
	 */
	protected T getDelegate() {
		return builder.build();
	}

	/**
	 * Creates new invocation instance for specified method with defined method interceptors.
	 *
	 * @param method method
	 * @return new invocation instance for specified method with defined method interceptors
	 */
	protected Invocation createInvocation(final Method method) {
		Invocation invocation = new InvocationImpl(getDelegate(), method);
		if (interceptors != null) {
			for (Interceptor advice : interceptors) {
				if (advice.supports(invocation)) {
					invocation = new InterceptedInvocation(invocation, advice);
				}
			}
		}
		return invocation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		db.append("interceptors", interceptors);
		return db.toString();
	}
}
