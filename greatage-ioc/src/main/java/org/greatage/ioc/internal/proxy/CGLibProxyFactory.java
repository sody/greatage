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

import net.sf.cglib.proxy.Enhancer;
import org.greatage.ioc.services.ObjectBuilder;

/**
 * This class represents proxy factory implementation using CGLib library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CGLibProxyFactory extends AbstractProxyFactory {

	/**
	 * {@inheritDoc}
	 */
	public <T> T createProxy(final ObjectBuilder<T> objectBuilder) {
		validate(objectBuilder);

		final Class superClass = objectBuilder.getObjectClass().isInterface() ?
				Object.class :
				objectBuilder.getObjectClass();
		final CGLibInvocationHandler<T> handler = new CGLibInvocationHandler<T>(objectBuilder);
		final Object proxy = objectBuilder.getObjectClass().isInterface() ?
				Enhancer.create(superClass, new Class[]{objectBuilder.getObjectClass()}, handler) :
				Enhancer.create(superClass, handler);
		return objectBuilder.getObjectClass().cast(proxy);
	}
}
