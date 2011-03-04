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

import java.lang.reflect.Method;

/**
 * This class represents {@link Invocation} implementation that is based on configured instance method invocation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MethodInvocation implements Invocation {
	private final Object instance;
	private final Method method;

	private final Method realMethod;

	/**
	 * Creates new instance of method invocation with defined object instance, service and implementation methods.
	 *
	 * @param instance object instance
	 * @param method   service method
	 */
	public MethodInvocation(final Object instance, final Method method) {
		this.instance = instance;
		this.method = method;

		try {
			this.realMethod = instance.getClass().getMethod(method.getName(), method.getParameterTypes());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Could not create invocation instance", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getRealMethod() {
		return realMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object proceed(final Object... parameters) throws Throwable {
		return method.invoke(instance, parameters);
	}
}
