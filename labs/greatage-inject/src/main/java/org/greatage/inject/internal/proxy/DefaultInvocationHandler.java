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

package org.greatage.inject.internal.proxy;

import org.greatage.inject.services.ServiceBuilder;
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
	private final ServiceBuilder<T> builder;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder, not null
	 */
	public DefaultInvocationHandler(final ServiceBuilder<T> builder) {
		this.builder = builder;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		if (builder.intercepts(method)) {
			return builder.invoke(method, args);
		}
		return method.invoke(builder.build(), args);
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
