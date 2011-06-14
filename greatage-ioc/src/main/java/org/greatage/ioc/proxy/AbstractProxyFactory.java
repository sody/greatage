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

import org.greatage.util.DescriptionBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents abstract proxy factory representation that helps to validate input parameters for creation
 * proxy.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractProxyFactory implements ProxyFactory {

	/**
	 * Checks if proxy instance can be created for specified class. It can not be created when original class is not
	 * interface and has no default constructor.
	 *
	 * @param <T>     object type
	 * @param builder object builder
	 * @throws IllegalArgumentException if proxy instance can not be created
	 */
	protected <T> void validate(final ObjectBuilder<T> builder) {
		if (builder == null) {
			throw new IllegalArgumentException("Object builder must be specified");
		}
		final Class<T> proxyClass = builder.getObjectClass();
		if (proxyClass == null) {
			throw new IllegalArgumentException("Object builder must be configured with proxy class");
		}
		if (!proxyClass.isInterface()) {
			try {
				proxyClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(
						String.format("Proxy class '%s' must have default constructor", proxyClass), e);
			}
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
