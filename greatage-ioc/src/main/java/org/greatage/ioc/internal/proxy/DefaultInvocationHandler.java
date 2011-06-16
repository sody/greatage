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

package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.Interceptor;
import org.greatage.ioc.Invocation;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @param <T> delegate type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultInvocationHandler<T> implements InvocationHandler {
	private final ObjectBuilder<T> builder;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder, not null
	 */
	public DefaultInvocationHandler(final ObjectBuilder<T> builder) {
		this.builder = builder;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final T target = builder.build();
		final Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
		final Invocation invocation = new InvocationImpl(target, realMethod);
		final Interceptor interceptor = builder.getInterceptor();
		if (interceptor != null) {
			return interceptor.invoke(invocation, args);
		}
		return invocation.proceed(args);
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
