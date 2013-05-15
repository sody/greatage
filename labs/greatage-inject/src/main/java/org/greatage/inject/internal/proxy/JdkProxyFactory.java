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

import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.ServiceBuilder;
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

	public <T> T createProxy(final ServiceBuilder<T> builder) {
		assert builder != null : "Object builder should be specified";

		final Class<T> serviceClass = builder.getMarker().getServiceClass();
		assert serviceClass != null : "Object class should be specified";
		if (!serviceClass.isInterface()) {
			throw new IllegalArgumentException("Object class should be an interface");
		}

		final ClassLoader classLoader = serviceClass.getClassLoader();
		final InvocationHandler handler = new DefaultInvocationHandler<T>(builder);
		final Object proxy = Proxy.newProxyInstance(classLoader, new Class<?>[]{serviceClass}, handler);

		return serviceClass.cast(proxy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
