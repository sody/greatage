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

import org.greatage.inject.Interceptor;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This class represents proxy factory implementation using JDK.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkProxyFactory implements ProxyFactory {

	public <T> T createProxy(final Class<T> objectClass,
							 final ObjectBuilder<T> builder,
							 final Interceptor interceptor) {
		assert builder != null : "Object builder should be specified";
		assert objectClass != null : "Object class should be specified";
		if (!objectClass.isInterface()) {
			throw new IllegalArgumentException("Object class should be an interface");
		}

		final ClassLoader classLoader = objectClass.getClassLoader();
		final InvocationHandler handler = interceptor != null ?
				new InterceptedInvocationHandler<T>(builder, interceptor) :
				new DefaultInvocationHandler<T>(builder);
		final Object proxy = Proxy.newProxyInstance(classLoader, new Class<?>[]{objectClass}, handler);

		return objectClass.cast(proxy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
