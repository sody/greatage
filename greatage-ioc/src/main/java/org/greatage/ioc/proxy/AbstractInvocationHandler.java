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

package org.greatage.ioc.proxy;

import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @param <T> delegate type
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractInvocationHandler<T> {
	private final ObjectBuilder<T> builder;

	private final Map<Method, Invocation> invocations = CollectionUtils.newConcurrentMap();

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder, not null
	 */
	protected AbstractInvocationHandler(final ObjectBuilder<T> builder) {
		this.builder = builder;
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
	protected Invocation getInvocation(final Method method) {
		if (!invocations.containsKey(method)) {
			try {
				final T target = getDelegate();
				final Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
				Invocation invocation = new InvocationImpl(target, realMethod);
				final List<Interceptor> interceptors = builder.getInterceptors();
				if (interceptors != null) {
					for (Interceptor interceptor : interceptors) {
						if (interceptor.supports(invocation)) {
							invocation = new InterceptedInvocation(invocation, interceptor);
						}
					}
				}
				invocations.put(method, invocation);
			}
			catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("Could not create invocation instance", e);
			}
		}
		return invocations.get(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		return db.toString();
	}
}
