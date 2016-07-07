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

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.util.DescriptionBuilder;

/**
 * This class represents proxy factory implementation using CGLib library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CGLibProxyFactory implements ProxyFactory {

	public <T> T createProxy(final ServiceBuilder<T> builder) {
		assert builder != null : "Object builder should be specified";

		final Class<T> serviceClass = builder.getMarker().getServiceClass();
		assert serviceClass != null : "Object class should be specified";

		if (!serviceClass.isInterface()) {
			try {
				serviceClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(
						String.format("Object class '%s' should have default constructor", serviceClass), e);
			}
		}

		final Class superClass = serviceClass.isInterface() ? Object.class : serviceClass;
		final InvocationHandler handler = new CGLibHandler<T>(builder);
		final Object proxy = serviceClass.isInterface() ?
				Enhancer.create(superClass, new Class[]{serviceClass}, handler) :
				Enhancer.create(superClass, handler);

		return serviceClass.cast(proxy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}

	public class CGLibHandler<T> extends DefaultInvocationHandler<T> implements InvocationHandler {

		/**
		 * Creates new instance of invocation handler for CGLib proxy objects.
		 *
		 * @param builder object builder
		 */
		CGLibHandler(final ServiceBuilder<T> builder) {
			super(builder);
		}
	}
}
