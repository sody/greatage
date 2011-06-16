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

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Proxy;

/**
 * This class represents proxy factory implementation using JDK.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkProxyFactory extends AbstractProxyFactory {

	/**
	 * {@inheritDoc}
	 */
	public <T> T createProxy(final ObjectBuilder<T> builder) {
		validate(builder);

		final ClassLoader classLoader = builder.getObjectClass().getClassLoader();

		final Object proxyInstance = Proxy.newProxyInstance(classLoader,
				new Class<?>[] { builder.getObjectClass() },
				new DefaultInvocationHandler<T>(builder));
		return builder.getObjectClass().cast(proxyInstance);
	}

	/**
	 * {@inheritDoc} It also can not be created when original class is not interface.
	 */
	@Override
	protected <T> void validate(final ObjectBuilder<T> builder) {
		super.validate(builder);
		if (!builder.getObjectClass().isInterface()) {
			throw new IllegalArgumentException("Proxy class must be an interface");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
